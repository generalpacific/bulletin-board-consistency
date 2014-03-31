package edu.umn.bulletinboard.server;

import java.net.MalformedURLException;
import java.rmi.Naming;
import java.rmi.NotBoundException;
import java.rmi.RemoteException;
import java.rmi.registry.LocateRegistry;
import java.util.HashSet;
import java.util.Set;
import java.util.concurrent.ExecutionException;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;
import java.util.concurrent.Future;

import edu.umn.bulletinboard.common.constants.RMIConstants;
import edu.umn.bulletinboard.common.exception.IllegalIPException;
import edu.umn.bulletinboard.common.rmi.BulletinBoardService;
import edu.umn.bulletinboard.common.rmi.RegisterRet;
import edu.umn.bulletinboard.common.server.ServerInfo;
import edu.umn.bulletinboard.common.util.LogUtil;
import edu.umn.bulletinboard.common.validator.ContentValidator;
import edu.umn.bulletinboard.server.coordinator.Coordinator;

/**
 * This is the entry point for the server.
 * The server first binds to the coordinator server 
 * Then it starts the rmi service so that the clients can bind and 
 * starts accepting the requests from client.
 * 
 * 
 * @author prashant
 *
 */
public class Server {
	
	private static final String CLASS_NAME = Server.class.getSimpleName();
	
	private static String serverIp = null;
	private static int serverRMIPort = -1;
	private static BulletinBoardService coordinatorServerRMIObjectHandle = null;
	private static int serverId = -1;
	private static Set<ServerInfo> servers = new HashSet<ServerInfo>();	
	
	public static void main(String args[]) {
		final String method = CLASS_NAME + ".main()";
		if(args.length != 3) {
			LogUtil.log(method, "Invalid cli arguments. Usage server <server ip> <server rmi port> <Configuration filename>");
			return;
		}
		
		if(!ContentValidator.isValidIp(args[0])) {
			LogUtil.log(method, args[0] + " is not a valid IP.");
			return;
		}
		serverIp = args[0];
		
		if(!ContentValidator.isValidPort(args[1])) {
			LogUtil.log(method, args[1] + " is not a valid Port.");
			return;
		}
		serverRMIPort = Integer.parseInt(args[1]);
		
		ServerConfig.loadProperties(args[2]);

		System.setProperty("java.rmi.server.hostname", serverIp);
		LogUtil.log(method, "Getting the CoordinatorServerRMIObject on " + ServerConfig.getCoordinatingServerIp() 
				+ ":" + ServerConfig.getCoordinatingServerRMIPort()); 
		try {
			if(!ServerConfig.isCoordinatingServer()) {
				coordinatorServerRMIObjectHandle = (BulletinBoardService) Naming.lookup("rmi://" + ServerConfig.getCoordinatingServerIp()
					+ ":" + ServerConfig.getCoordinatingServerRMIPort() + "/"
					+ RMIConstants.BB_SERVICE);
			}else{
				coordinatorServerRMIObjectHandle = BulletinBoardServiceImpl.getInstance();
			}
		} catch (MalformedURLException e) {
			LogUtil.log(method, "Got exception " + e.getMessage() + ". Exiting.");
			System.exit(1);
		} catch (RemoteException e) {
			LogUtil.log(method, "Got exception " + e.getMessage() + ". Exiting.");
			System.exit(1);
		} catch (NotBoundException e) {
			LogUtil.log(method, "Got exception " + e.getMessage() + ". Exiting.");
			System.exit(1);
		}
		LogUtil.log(method, "DONE getting the CoordinatorServerRMIObject");
		
		LogUtil.log(method, "Registering to the coordinating server");
		try {
			if(!ServerConfig.isCoordinatingServer()) {
				RegisterRet ret = coordinatorServerRMIObjectHandle.register(serverIp, serverRMIPort);
				serverId = ret.getId();
		        servers = new HashSet<ServerInfo>(ret.getServers());
			}else{
				serverId = 99;
				try {
					Coordinator.getInstance().getServerMap().put(99, new ServerInfo(serverIp, serverRMIPort));
				} catch (IllegalIPException e) {
					// TODO Auto-generated catch block
					e.printStackTrace();
				}
			}
		} catch (RemoteException e1) {
			LogUtil.log(method, "Cannot register to the coordinating server " + e1.getMessage() + ". Exiting.");
			System.exit(1);
		}
		LogUtil.log(method, "DONE Registering to the coordinating server");
		
		LogUtil.log(method, "Starting server on " + serverIp + ":" + serverRMIPort);
		LogUtil.log(method, "Binding " + RMIConstants.BB_SERVICE);
		// Bind the PubSubService RMI Registry
		try {
			LocateRegistry.createRegistry(serverRMIPort);
			Naming.rebind(RMIConstants.BB_SERVICE, BulletinBoardServiceImpl.getInstance());
		} catch (RemoteException e) {
			LogUtil.log(method, "Got exception " + e.getMessage() + ". Exiting.");
			System.exit(1);
		} catch (MalformedURLException e) {
			LogUtil.log(method, "Got exception " + e.getMessage() + ". Exiting.");
			System.exit(1);
		}
		LogUtil.log(method, "DONE Binding " + RMIConstants.BB_SERVICE);
		
		if(ServerConfig.isCoordinatingServer()) {
			LogUtil.log(method, "Starting the sync thread for coordinator");
			ExecutorService executorService = Executors.newSingleThreadExecutor();
			Future<Boolean> submit = executorService.submit(new CoordinatorSyncThread());
			try {
				submit.get();
			} catch (InterruptedException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			} catch (ExecutionException e) {
				LogUtil.log(method, "Got exception in sync thread : " + e.getMessage());
				Throwable cause = e.getCause();
				if(cause != null) {
					LogUtil.log(method, "Exception Details: " + cause.getMessage());
				}
			}
			LogUtil.log(method, "DONE Starting the sync thread for coordinator");
		}
	}

	public static BulletinBoardService getCoodinatorServerRMIObjectHandle() {
		return coordinatorServerRMIObjectHandle;
	}
	
	public static Set<ServerInfo> getServers() {
		return servers;
	}
	
	public static int getServerId() {
		return serverId;
	}
}
