package edu.umn.bulletinboard.server;

import java.rmi.RemoteException;
import java.util.List;

import edu.umn.bulletinboard.common.content.Article;
import edu.umn.bulletinboard.common.content.RegisterRet;
import edu.umn.bulletinboard.common.exception.IllegalIPException;
import edu.umn.bulletinboard.common.rmi.BulletinBoardService;
import edu.umn.bulletinboard.common.server.ServerInfo;
import edu.umn.bulletinboard.common.util.ConsistencyType;
import edu.umn.bulletinboard.server.coordinator.Coordinator;

/**
 * Created by Abhijeet on 3/29/2014.
 *
 *
 */
public class BulletinBoardServiceImpl implements BulletinBoardService{

    private static BulletinBoardServiceImpl bb = null;
    private final ServerBulletinBoardServiceImpl serverImpl = new ServerBulletinBoardServiceImpl();
    private final Coordinator coordServerImpl = new Coordinator();

    private BulletinBoardServiceImpl() {
    }

    public synchronized static BulletinBoardServiceImpl getInstance() {
        if (null == bb) {
            bb = new BulletinBoardServiceImpl();
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
        return coordServerImpl.readFromCoordinatingServer(type);
    }

    @Override
    public Article chooseFromCoordinatingServer(int id, ConsistencyType type) throws RemoteException {
        return coordServerImpl.chooseFromCoordinatingServer(id, type);
    }

    @Override
    public int writeToCoordinatingServer(Article articleText, ConsistencyType type) throws RemoteException {
        return coordServerImpl.writeToCoordinatingServer(articleText, type);
    }

    @Override
    public int replyToCoordinatingServer(int articleId, ConsistencyType type) throws RemoteException {
        return coordServerImpl.replyToCoordinatingServer(articleId, type);
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
    	coordServerImpl.sync(articles);
    }

    @Override
    public void writeToServer(int articleId, String articleText) throws RemoteException {
    	serverImpl.writeToServer(articleId, articleText);
    }

    @Override
    public String readFromServer(int articleId) throws RemoteException {
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
		return coordServerImpl.getRegisteredServers();
	}

	@Override
	public List<Article> readFromServer() throws RemoteException {
		// TODO Auto-generated method stub
		return null;
	}
}
