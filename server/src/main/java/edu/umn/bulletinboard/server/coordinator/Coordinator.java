package edu.umn.bulletinboard.server.coordinator;

import java.net.MalformedURLException;
import java.rmi.Naming;
import java.rmi.NotBoundException;
import java.rmi.RemoteException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.HashSet;
import java.util.List;
import java.util.Map;
import java.util.Random;
import java.util.Set;

import edu.umn.bulletinboard.common.constants.RMIConstants;
import edu.umn.bulletinboard.common.content.Article;
import edu.umn.bulletinboard.common.exception.IllegalIPException;
import edu.umn.bulletinboard.common.locks.ServerLock;
import edu.umn.bulletinboard.common.rmi.BulletinBoardService;
import edu.umn.bulletinboard.common.rmi.RegisterRet;
import edu.umn.bulletinboard.common.server.ServerInfo;
import edu.umn.bulletinboard.common.util.ConsistencyType;
import edu.umn.bulletinboard.common.util.LogUtil;
import edu.umn.bulletinboard.server.BulletinBoardServiceImpl;
import edu.umn.bulletinboard.server.Server;
import edu.umn.bulletinboard.server.ServerConfig;
import edu.umn.bulletinboard.server.exceptions.InvalidArticleException;

/**
 * @author abhijeet
 *
 * This is a RMI service implementation for the coordinator and caters to requests from
 * servers. Actual service acts as proxy and forwards the calls here.
 *
 * This is a bit tricky, the implementation takes care of all the inwards as well as
 * outwards call to Coordinator. So, we cannot call this just coordinator service impl.
 *
 */
public class Coordinator {

	private static final String CLASS_NAME = Coordinator.class.getSimpleName();
    //This is an assumption that number of articles cannot be more than
    //Max value of integer in Java
	int counter, serverIdCounter;
    Map<Integer, ServerInfo> servers = new HashMap<Integer, ServerInfo>();

    private static Coordinator instance = null;

    private Coordinator() {}

    public synchronized  static Coordinator getInstance() {
        if (null == instance) {
            instance = new Coordinator();
        }

        return instance;
    }

    /**
     * Read call from server. Should be mostly used in Quorum consistency.
     *
     * @param type
     * @return
     * @throws RemoteException
     */
	public synchronized  List<Article> readFromCoordinatingServer(ConsistencyType type)
            throws RemoteException, MalformedURLException, NotBoundException {

		final String method = CLASS_NAME + ".readFromCoordinatingServer()";
    	LogUtil.log(method,"Server:"+  Server.getServerId() + " "+  "Reading from coordinating server");
        //for quorum consistency only
        //TODO: pick up consistency level from properties file
        if (type != ConsistencyType.QUORUM) {
            throw new RemoteException("Not a quorum consistency");
        }

        //see which is the max value, get all the values from that server and return them
        int latestUpdatedServerId = getLatestUpdatedServerId();
		BulletinBoardService client = getClient(servers.get(latestUpdatedServerId), latestUpdatedServerId);
        return client.readFromServer();
	}

    private synchronized int getLatestUpdatedServerId() throws RemoteException, NotBoundException
            , MalformedURLException {
        Random random = new Random();

        int max = 0, maxServer = 0;
        for (int i = 0; i < getNR(); ++i) {
            int servId = random.nextInt(serverIdCounter + 2); //exclusive

            if (servId == serverIdCounter + 1) {
                servId = 99;
            }

            if (0 == servId || null == servers.get(servId)) {
                --i;
                continue;
            }

            BulletinBoardService client = getClient(servers.get(servId),servId);
            if (client.getLatestArticleId() > max) {
                max = client.getLatestArticleId();
                maxServer = servId;
            }
        }

        return maxServer;
    }

    private BulletinBoardService getClient(ServerInfo sInfo, int serverId) throws RemoteException
            , NotBoundException, MalformedURLException {
    	final String method = CLASS_NAME + ".getClient()";
        BulletinBoardService client = null;
        if (99 == serverId) {
            client = BulletinBoardServiceImpl.getInstance();
        } else {
        	LogUtil.log(method, "Server:"+  Server.getServerId() + " "+ "Getting client id "  + serverId + " address:"+sInfo.getIp()+":"
        			+ sInfo.getPort());
            client = (BulletinBoardService) Naming.lookup("rmi://"
                    + sInfo.getIp() + ":" + sInfo.getPort()
                    + "/" + RMIConstants.BB_SERVICE);
        }

        return client;
    }

    /**
     * Choose call from server.
     *
     * @param id
     * @param type
     * @return
     * @throws RemoteException
     */
	public synchronized  Article chooseFromCoordinatingServer(int id, ConsistencyType type)
            throws RemoteException, MalformedURLException, NotBoundException {

		final String method = CLASS_NAME + ".chooseFromCoordinatingServer()";
    	LogUtil.log(method, "Server:"+  Server.getServerId() + " "+ "Choose from coordinating server for id : " + id);
        //for quorum consistency only
        //TODO: pick up consistency level from properties file
        if (type != ConsistencyType.QUORUM) {
            throw new RemoteException("Not a quorum consistency");
        }

        int latestUpdatedServerId = getLatestUpdatedServerId();

        //see which is the max value, get all the values from that server and return them
        BulletinBoardService client = getClient(servers.get(latestUpdatedServerId),latestUpdatedServerId);
        return client.readFromServer(id);
	}

    private synchronized void syncAll(int id, Article article) throws RemoteException, NotBoundException
            , MalformedURLException {
    	
    	final String method = CLASS_NAME + ".syncAll()";
    	LogUtil.log(method, "Server:"+  Server.getServerId() + " "+ "Syncing ALL : "  + id  + ":" + article);
        for (int i : servers.keySet()) {
        	LogUtil.log(method, "Server:"+  Server.getServerId() + " "+ "Syncing to server: "  + i);
            BulletinBoardService client = getClient(servers.get(i),i);
            if (-1 == id) {
                client.writeToServer(article);
            } else {
                client.replyToServer(id, article);
            }
        }
    }

    /**
     * Write call from Server.
     *
     * @param articleText
     * @param type
     * @return
     * @throws RemoteException
     */
    public synchronized  int writeToCoordinatingServer(Article articleText, ConsistencyType type)
            throws RemoteException, MalformedURLException, NotBoundException, InvalidArticleException {
    	final String method = CLASS_NAME + ".writeToCoordinatingServer()";
    	LogUtil.log(method,"Server:"+  Server.getServerId() + " "+  "Writing " +  articleText + " to coordinating server");
        return writeReply(-1, articleText, type);
    }

    private synchronized  int writeReply(int id, Article articleText, ConsistencyType type)
            throws RemoteException, InvalidArticleException, MalformedURLException
            , NotBoundException {
    	final String method = CLASS_NAME + ".writeReply()";
    	LogUtil.log(method, "Server:"+  Server.getServerId() + " "+ "Writing "  + id + ":" + articleText);
        if (! (type == ConsistencyType.QUORUM || type == ConsistencyType.SEQUENTIAL)) {
            throw new RemoteException("Not a quorum/sequential consistency");
        }

        articleText.setId(++counter);

        if (type == ConsistencyType.SEQUENTIAL) {
            syncAll(id, articleText);
            return counter;
        }

        //quorum consistency
        //add the article

        Random random = new Random();
        for (int i = 0; i < getNW(); ++i) {
            int servId = random.nextInt(serverIdCounter + 2); //exclusive

            if (servId == serverIdCounter + 1) {
                servId = 99;
            }

            if (0 == servId || null == servers.get(servId)) {
                --i;
                continue;
            }

            BulletinBoardService client = getClient(servers.get(servId), servId);
            if (-1 == id) {
                client.writeToServer(articleText);
            } else {
                client.replyToServer(id, articleText);
            }
        }

        return counter;

    }

    /**
     * Reply call from server.
     *
     * @param articleId
     * @param article
     * @param type
     * @return
     * @throws RemoteException
     */
    public synchronized  int replyToCoordinatingServer(int articleId, Article article
            , ConsistencyType type) throws RemoteException, InvalidArticleException
            , NotBoundException, MalformedURLException {
    	final String method = CLASS_NAME + ".replyToCoordinatingServer()";
    	LogUtil.log(method,"Server:"+  Server.getServerId() + " "+  "Replying " + article + " to article id: " + articleId + " in coordinating server");
        return writeReply(articleId, article, type);
    }

    /**
     * Generate unique Article ID and send it over to server.
     *
     * @return article id
     * @throws RemoteException
     */
	public synchronized  int getNextArticleID() throws RemoteException {

        // this should be  as a lot of Servers will simultaneously
        // call this method.
        synchronized (ServerLock.getID) {
            return ++counter;
        }
	}

    /**
     * Register a new server. When a server starts up, it should register with
     * coordinator.
     *
     * @return server id
     * @throws RemoteException
     */
    public synchronized   RegisterRet register(String ip, int port) throws RemoteException {

        RegisterRet ret = null;

         synchronized (ServerLock.register) {
            ++serverIdCounter;
            try {
                servers.put(serverIdCounter, new ServerInfo(ip, port));
                ret = new RegisterRet(serverIdCounter, new ArrayList<ServerInfo>(
                        servers.values()));
            } catch (IllegalIPException e) {
                throw new RemoteException(e.getMessage());
            }
        }

        return ret;
    }

    private int getNR() {
        return ServerConfig.getNR();
    }

    private int getNW() {
        if (-1 == ServerConfig.getNW()) {
            return servers.size();
        }
        return ServerConfig.getNW();
    }
    
    public synchronized  Set<ServerInfo> getServers() {
    	return new HashSet(servers.values());
    }
    
    public Map<Integer, ServerInfo> getServerMap() {
    	return servers;
    }
}
