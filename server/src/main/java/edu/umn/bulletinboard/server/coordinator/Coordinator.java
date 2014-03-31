package edu.umn.bulletinboard.server.coordinator;

import java.rmi.RemoteException;
import java.util.ArrayList;
import java.util.List;

import edu.umn.bulletinboard.common.content.Article;
import edu.umn.bulletinboard.common.content.RegisterRet;
import edu.umn.bulletinboard.common.exception.IllegalIPException;
import edu.umn.bulletinboard.common.locks.ServerLock;
import edu.umn.bulletinboard.common.server.ServerInfo;
import edu.umn.bulletinboard.common.util.ConsistencyType;
import edu.umn.bulletinboard.common.util.LogUtil;

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

    //This is an assumption that number of articles cannot be more than
    //Max value of integer in Java
	int counter, serverIdCounter;
    int readQ, writeQ;
    List<ServerInfo> servers = new ArrayList<ServerInfo>();

    /**
     * Read call from server. Should be mostly used in Quorum consistency.
     *
     * @param type
     * @return
     * @throws RemoteException
     */
	public List<Article> readFromCoordinatingServer(ConsistencyType type)
			throws RemoteException {

        //for quorum consistency only

        //get all the data from any random readQ servers.
        //see which is the max value, get all the values from that server and return them

		return null;
	}

    /**
     * Choose call from server.
     *
     * @param id
     * @param type
     * @return
     * @throws RemoteException
     */
	public Article chooseFromCoordinatingServer(int id, ConsistencyType type)
			throws RemoteException {

        //choose for server? means what?

		return null;
	}

    /**
     * Write call from Server.
     *
     * @param articleText
     * @param type
     * @return
     * @throws RemoteException
     */
    public int writeToCoordinatingServer(Article articleText, ConsistencyType type)
            throws RemoteException {

        return 0;
    }

    /**
     * Reply call from server.
     *
     * @param articleId
     * @param type
     * @return
     * @throws RemoteException
     */
    public int replyToCoordinatingServer(int articleId, ConsistencyType type)
            throws RemoteException {

        return 0;
    }

    /**
     * Generate unique Article ID and send it over to server.
     *
     * @return article id
     * @throws RemoteException
     */
	public int getNextArticleID() throws RemoteException {

        // this should be synchronized as a lot of Servers will simultaneously
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
    public RegisterRet register(String ip, int port) throws RemoteException {

        RegisterRet ret = null;

        synchronized (ServerLock.register) {
            ++serverIdCounter;
            try {
                ret = new RegisterRet(serverIdCounter, servers);
                servers.add(serverIdCounter, new ServerInfo(ip, port));
            } catch (IllegalIPException e) {
                throw new RemoteException(e.getMessage());
            }
        }

        return ret;
    }

    public void sync(List<Article> articles) throws RemoteException {

    }
    
    public List<ServerInfo> getRegisteredServers() throws RemoteException {
		return null;
	}
}
