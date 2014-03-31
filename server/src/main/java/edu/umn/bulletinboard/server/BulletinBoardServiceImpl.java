package edu.umn.bulletinboard.server;

import java.net.MalformedURLException;
import java.rmi.NotBoundException;
import java.rmi.RemoteException;
import java.rmi.server.UnicastRemoteObject;
import java.util.List;

import com.sun.org.apache.xml.internal.utils.SerializableLocatorImpl;

import edu.umn.bulletinboard.common.content.Article;
import edu.umn.bulletinboard.common.exception.IllegalIPException;
import edu.umn.bulletinboard.common.rmi.BulletinBoardService;
import edu.umn.bulletinboard.common.rmi.RegisterRet;
import edu.umn.bulletinboard.common.server.ServerInfo;
import edu.umn.bulletinboard.common.util.ConsistencyType;
import edu.umn.bulletinboard.server.coordinator.Coordinator;
import edu.umn.bulletinboard.server.exceptions.InvalidArticleException;

/**
 * Created by Abhijeet on 3/29/2014.
 *
 *
 */
public class BulletinBoardServiceImpl extends UnicastRemoteObject implements BulletinBoardService{

	private static final long serialVersionUID = -5647882858583434862L;
	
	private static BulletinBoardServiceImpl bb = null;
    private final ServerBulletinBoardServiceImpl serverImpl = new ServerBulletinBoardServiceImpl();
    private final Coordinator coordServerImpl = Coordinator.getInstance();

    private BulletinBoardServiceImpl() throws RemoteException
    {
    }

    public synchronized static BulletinBoardServiceImpl getInstance() {
        if (null == bb) {
            try {
				bb = new BulletinBoardServiceImpl();
			} catch (RemoteException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
        }
        return bb;
    }

    @Override
    public int post(String article) throws RemoteException {
        return serverImpl.post(article);
    }

    @Override
    public String read() throws RemoteException {
        return serverImpl.read();
    }

    @Override
    public Article choose(int id) throws RemoteException {
        return serverImpl.choose(id);
    }

    @Override
    public int reply(int id, Article reply) throws RemoteException {
        return serverImpl.reply(id, reply);
    }

    @Override
    public List<Article> readFromCoordinatingServer(ConsistencyType type) throws RemoteException {
        try {
			return coordServerImpl.readFromCoordinatingServer(type);
		} catch (MalformedURLException e) {
			throw new RemoteException("Got exception in connection to other servers.",e);
		} catch (NotBoundException e) {
			throw new RemoteException("Got exception in connection to other servers.",e);
		}
    }

    @Override
    public Article chooseFromCoordinatingServer(int id, ConsistencyType type) throws RemoteException {
        try {
			return coordServerImpl.chooseFromCoordinatingServer(id, type);
		} catch (MalformedURLException e) {
			throw new RemoteException("Got exception in connection to other servers.",e);
		} catch (NotBoundException e) {
			throw new RemoteException("Got exception in connection to other servers.",e);
		}
    }

    @Override
    public int writeToCoordinatingServer(Article articleText, ConsistencyType type) throws RemoteException {
        try {
			return coordServerImpl.writeToCoordinatingServer(articleText, type);
		} catch (MalformedURLException e) {
			throw new RemoteException("Got exception in connection to other servers.",e);
		} catch (NotBoundException e) {
			throw new RemoteException("Got exception in connection to other servers.",e);
		} catch (InvalidArticleException e) {
			throw new RemoteException("Got exception in connection to other servers.",e);
		}
    }

    @Override
    public int replyToCoordinatingServer(int articleId, Article article, ConsistencyType type) throws RemoteException {
        try {
			return coordServerImpl.replyToCoordinatingServer(articleId, article, type);
		} catch (MalformedURLException e) {
			throw new RemoteException("Got exception in connection to other servers.",e);
		} catch (InvalidArticleException e) {
			throw new RemoteException("Got exception in connection to other servers.",e);
		} catch (NotBoundException e) {
			throw new RemoteException("Got exception in connection to other servers.",e);
		}
    }

    @Override
    public int getNextArticleID() throws RemoteException {
        return coordServerImpl.getNextArticleID();
    }

    @Override
    public RegisterRet register(String ip, int port) throws RemoteException {
        return coordServerImpl.register(ip, port);
    }

    @Override
    public void sync(List<Article> articles) throws RemoteException {
    	serverImpl.sync(articles);
    }

    @Override
    public void writeToServer(Article article) throws RemoteException {
    	serverImpl.writeToServer(article.getId(), article.getText());
    }

    @Override
    public Article readFromServer(int articleId) throws RemoteException {
        return serverImpl.readFromServer(articleId);
    }

    @Override
    public int getLatestArticleId() throws RemoteException {
        return serverImpl.getLatestArticleId();
    }

    @Override
    public void addServer(int serverId) throws RemoteException {
    	try {
			serverImpl.addServer(serverId);
		} catch (IllegalIPException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
    }

	@Override
	public List<ServerInfo> getRegisteredServers() throws RemoteException {
		//TODO
		return null;
	}

	@Override
	public List<Article> readFromServer() throws RemoteException {
		return serverImpl.readFromServer();
	}

	@Override
	public void replyToServer(int id, Article article) throws RemoteException {
		// TODO Auto-generated method stub
		
	}
}
