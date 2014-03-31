package edu.umn.bulletinboard.server;

import java.net.MalformedURLException;
import java.rmi.NotBoundException;
import java.rmi.RemoteException;
import java.rmi.server.UnicastRemoteObject;
import java.util.List;

import edu.umn.bulletinboard.common.content.Article;
import edu.umn.bulletinboard.common.exception.IllegalIPException;
import edu.umn.bulletinboard.common.rmi.BulletinBoardService;
import edu.umn.bulletinboard.common.rmi.RegisterRet;
import edu.umn.bulletinboard.common.server.ServerInfo;
import edu.umn.bulletinboard.common.util.ConsistencyType;
import edu.umn.bulletinboard.common.util.LogUtil;
import edu.umn.bulletinboard.server.coordinator.Coordinator;
import edu.umn.bulletinboard.server.exceptions.InvalidArticleException;
import edu.umn.bulletinboard.server.storage.MemStore;

/**
 * Created by Abhijeet on 3/29/2014.
 *
 *
 */
public class BulletinBoardServiceImpl extends UnicastRemoteObject implements BulletinBoardService{

	private static final String CLASS_NAME = BulletinBoardServiceImpl.class.getSimpleName();
	
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
    	final String method = CLASS_NAME + ".post()";
    	LogUtil.log(method, "Server:"+Server.getServerId()+" "+ "Post article: " + article);
        return serverImpl.post(article);
    }

    @Override
    public String read() throws RemoteException {
    	final String method = CLASS_NAME + ".read()";
    	LogUtil.log(method, "Server:"+Server.getServerId()+" "+ "Reading articles");
        return serverImpl.read();
    }

    @Override
    public Article choose(int id) throws RemoteException {
    	final String method = CLASS_NAME + ".choose()";
    	LogUtil.log(method, "Server:"+Server.getServerId()+" "+ "Choose for id: " + id);
        return serverImpl.choose(id);
    }

    @Override
    public int reply(int id, Article reply) throws RemoteException {
    	final String method = CLASS_NAME + ".reply()";
    	LogUtil.log(method, "Server:"+Server.getServerId()+" "+ "Reply : " + reply + " for id: " + id);
        return serverImpl.reply(id, reply);
    }

    @Override
    public List<Article> readFromCoordinatingServer(ConsistencyType type) throws RemoteException {
    	final String method = CLASS_NAME + ".readFromCoordinatingServer()";
    	LogUtil.log(method, "Server:"+Server.getServerId()+" "+ "Reading from Coordinating server");
        try {
			return coordServerImpl.readFromCoordinatingServer(type);
		} catch (MalformedURLException e) {
			LogUtil.log(method,"Got exception : " + e.getMessage());
			throw new RemoteException("Got exception in connection to other servers.",e);
		} catch (NotBoundException e) {
			LogUtil.log(method,"Got exception : " + e.getMessage());
			throw new RemoteException("Got exception in connection to other servers.",e);
		}
    }

    @Override
    public Article chooseFromCoordinatingServer(int id, ConsistencyType type) throws RemoteException {
    	final String method = CLASS_NAME + ".chooseFromCoordinatingServer()";
    	LogUtil.log(method, "Server:"+Server.getServerId()+" "+ "Choosing from Coordinating server for id : " + id);
        try {
			return coordServerImpl.chooseFromCoordinatingServer(id, type);
		} catch (MalformedURLException e) {
			LogUtil.log(method,"Got exception : " + e.getMessage());
			throw new RemoteException("Got exception in connection to other servers.",e);
		} catch (NotBoundException e) {
			LogUtil.log(method,"Got exception : " + e.getMessage());
			throw new RemoteException("Got exception in connection to other servers.",e);
		}
    }

    @Override
    public int writeToCoordinatingServer(Article articleText, ConsistencyType type) throws RemoteException {
    	final String method = CLASS_NAME + ".writeToCoordinatingServer()";
    	LogUtil.log(method, "Server:"+Server.getServerId()+" "+ "Writing to Coordinating server " + articleText);
        try {
			return coordServerImpl.writeToCoordinatingServer(articleText, type);
		} catch (MalformedURLException e) {
			LogUtil.log(method,"Got exception : " + e.getMessage());
			throw new RemoteException("Got exception in connection to other servers.",e);
		} catch (NotBoundException e) {
			LogUtil.log(method,"Got exception : " + e.getMessage());
			throw new RemoteException("Got exception in connection to other servers.",e);
		} catch (InvalidArticleException e) {
			LogUtil.log(method,"Got exception : " + e.getMessage());
			throw new RemoteException("Got exception in connection to other servers.",e);
		}
    }

    @Override
    public int replyToCoordinatingServer(int articleId, Article article, ConsistencyType type) throws RemoteException {
    	final String method = CLASS_NAME + ".replyToCoordinatingServer()";
    	LogUtil.log(method, "Server:"+Server.getServerId()+" "+ "Replying to: "+ articleId+" article : "+article+" in Coordinating server");
        try {
			return coordServerImpl.replyToCoordinatingServer(articleId, article, type);
		} catch (MalformedURLException e) {
			LogUtil.log(method,"Got exception : " + e.getMessage());
			throw new RemoteException("Got exception in connection to other servers.",e);
		} catch (InvalidArticleException e) {
			LogUtil.log(method,"Got exception : " + e.getMessage());
			throw new RemoteException("Got exception in connection to other servers.",e);
		} catch (NotBoundException e) {
			LogUtil.log(method,"Got exception : " + e.getMessage());
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
		final String method = CLASS_NAME + ".replyToServer()";
		LogUtil.log(method, "Server:" + Server.getServerId()+ " Replying to article:"+id+" reply:"+article);
		try {
			MemStore.getInstance().addArticle(article);
		} catch (InvalidArticleException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		MemStore.getInstance().getArticle(id).getReplies().add(article.getId());
		LogUtil.log(method, "Server:" + Server.getServerId()+ " Updated memstore:" + MemStore.getInstance().getAllArticles().toString());
	}
}
