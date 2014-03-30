package edu.umn.bulletinboard.server;

import java.rmi.RemoteException;
import java.util.List;

import edu.umn.bulletinboard.common.content.Article;
import edu.umn.bulletinboard.common.util.ConsistencyType;

public class CoordinatorServerBulletinBoardServiceImpl {
	    public List<Article> readFromCoordinatingServer(ConsistencyType type) throws RemoteException {
	        return null;
	    }

	    public Article chooseFromCoordinatingServer(int id, ConsistencyType type) throws RemoteException {
	        return null;
	    }

	    public int writeToCoordinatingServer(Article articleText, ConsistencyType type) throws RemoteException {
	        return 0;
	    }

	    public int replyToCoordinatingServer(int articleId, ConsistencyType type) throws RemoteException {
	        return 0;
	    }

	    public int getNextArticleID() throws RemoteException {
	        return 0;
	    }

	    public int register(String ip, int port) throws RemoteException {
	        return 0;
	    }

	    public void sync(List<Article> articles) throws RemoteException {

	    }
}
