package edu.umn.bulletinboard.server;

import java.net.MalformedURLException;
import java.rmi.Naming;
import java.rmi.NotBoundException;
import java.rmi.RemoteException;
import java.rmi.registry.LocateRegistry;

import edu.umn.bulletinboard.common.constants.RMIConstants;
import edu.umn.bulletinboard.common.rmi.ServerCoordinatorCommunicate;
import edu.umn.bulletinboard.common.util.LogUtil;
import edu.umn.bulletinboard.common.validator.ContentValidator;

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
	
	private static String coordinatingServerIp = null;
	private static int coordinatingServerRMIPort = -1;
	private static String serverIp = null;
	private static int serverRMIPort = -1;
	private static ServerCoordinatorCommunicate coordinatorServerRMIObjectHandle = null;
	
	public static void main(String args[]) {
		final String method = CLASS_NAME + ".main()";
		if(args.length != 4) {
			LogUtil.log(method, "Invalid cli arguments. Usage server <co-ordinating server> <co-ordinating server rmi port> <server ip> <server rmi port>");
			return;
		}
		
		if(!ContentValidator.isValidIp(args[0])) {
			LogUtil.log(method, args[0] + " is not a valid IP.");
			return;
		}
		coordinatingServerIp = args[0];
		
		if(!ContentValidator.isValidPort(args[1])) {
			LogUtil.log(method, args[1] + " is not a valid Port.");
			return;
		}
		coordinatingServerRMIPort = Integer.parseInt(args[1]);
		
		if(!ContentValidator.isValidIp(args[2])) {
			LogUtil.log(method, args[2] + " is not a valid IP.");
			return;
		}
		serverIp = args[2];
		
		if(!ContentValidator.isValidPort(args[3])) {
			LogUtil.log(method, args[3] + " is not a valid Port.");
			return;
		}
		serverRMIPort = Integer.parseInt(args[3]);
		
		try {
			coordinatorServerRMIObjectHandle = (ServerCoordinatorCommunicate) Naming.lookup("rmi://" + coordinatingServerIp
					+ ":" + coordinatingServerRMIPort + "/"
					+ RMIConstants.COORDINATOR_BB_SERVICE);
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
		
		LogUtil.log(method, "Starting server on " + serverIp);
		System.setProperty("java.rmi.server.hostname", serverIp);
		LogUtil.log(method, "Binding " + RMIConstants.BB_SERVICE);
		// Bind the PubSubService RMI Registry
		try {
			LocateRegistry.createRegistry(serverRMIPort);
			Naming.rebind(RMIConstants.BB_SERVICE, BulletinBoardService.getInstance());
		} catch (RemoteException e) {
			LogUtil.log(method, "Got exception " + e.getMessage() + ". Exiting.");
			System.exit(1);
		} catch (MalformedURLException e) {
			LogUtil.log(method, "Got exception " + e.getMessage() + ". Exiting.");
			System.exit(1);
		}
		LogUtil.log(method, "DONE Binding " + RMIConstants.BB_SERVICE);	
	}
	
	public static ServerCoordinatorCommunicate getCoodinatorServerRMIObjectHandle() {
		return coordinatorServerRMIObjectHandle;
	}
}
