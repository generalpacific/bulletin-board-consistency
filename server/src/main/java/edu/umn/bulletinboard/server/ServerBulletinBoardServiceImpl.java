package edu.umn.bulletinboard.server;

import java.rmi.RemoteException;
import java.util.List;

import edu.umn.bulletinboard.common.content.Article;
import edu.umn.bulletinboard.common.server.ServerInfo;

public class ServerBulletinBoardServiceImpl {
	    public int post(String article) throws RemoteException {
	        return 0;
	    }

	    public String read() throws RemoteException {
	        return null;
	    }

	    public Article choose(int id) throws RemoteException {
	        return null;
	    }

	    public int reply(int id, Article reply) throws RemoteException {
	        return 0;
	    }
	    
	    public void writeToServer(int articleId, String articleText) throws RemoteException {

	    }

	    public String readFromServer(int articleId) throws RemoteException {
	        return null;
	    }

	    public int getLatestArticleId() throws RemoteException {
	        return 0;
	    }

	    public void addServer(int serverId) throws RemoteException {

	    }

		public List<ServerInfo> getRegisteredServers() throws RemoteException {
			// TODO Auto-generated method stub
			return null;
		}

}
