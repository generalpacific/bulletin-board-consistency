package edu.umn.bulletinboard.client;

import java.rmi.RemoteException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import edu.umn.bulletinboard.common.content.Article;
import edu.umn.bulletinboard.common.content.RegisterRet;
import edu.umn.bulletinboard.common.rmi.BulletinBoardService;
import edu.umn.bulletinboard.common.server.ServerInfo;
import edu.umn.bulletinboard.common.util.ConsistencyType;

/**
 * Server Mocked to test client only.
 * 
 * @author Abhijeet
 *
 */
public class MCServer implements BulletinBoardService {
	
	/**
	 * This is an append only list, items will never be
	 * removed from this. So,
	 */
	private List<Article> articles = new ArrayList<Article>();
	
	private Map<Integer, List<Integer>> articleMap = new HashMap<Integer, List<Integer>>();
	private int counter = 0;

    @Override
    public int post(String article) throws RemoteException {

        articles.add(new Article(++counter, article));
        return counter;
    }

    @Override
	public String read() throws RemoteException {
		return articles.toString();
	}

	@Override
	public Article choose(int id) throws RemoteException {
		// As the list is append only and counter increments by 1 only always
		// we are sure to find the reuired article at position id - 1 
		// (article id starts from 1). This is a simple trick to get an article in
		// constant time. 
		return articles.get(id - 1);
	}

	@Override
	public int reply(int id, Article reply) throws RemoteException {
		int newId = post(reply.getText());
		
		if (articleMap.get(id) == null) {
			List<Integer> list = new ArrayList<Integer>();
			list.add(newId);
			articleMap.put(id, list);
		} else {
			articleMap.get(id).add(newId);
		}
		
		return newId;
	}

    @Override
    public List<Article> readFromCoordinatingServer(ConsistencyType type) throws RemoteException {
        return null;
    }

    @Override
    public Article chooseFromCoordinatingServer(int id, ConsistencyType type) throws RemoteException {
        return null;
    }

    @Override
    public int writeToCoordinatingServer(Article articleText, ConsistencyType type) throws RemoteException {
        return 0;
    }

    @Override
    public int replyToCoordinatingServer(int articleId, ConsistencyType type) throws RemoteException {
        return 0;
    }

    @Override
    public int getNextArticleID() throws RemoteException {
        return 0;
    }

    @Override
    public RegisterRet register(String ip, int port) throws RemoteException {
        return null;
    }

    @Override
    public void sync(List<Article> articles) throws RemoteException {

    }

    @Override
    public void writeToServer(int articleId, String articleText) throws RemoteException {

    }

    @Override
    public String readFromServer(int articleId) throws RemoteException {
        return null;
    }

    @Override
    public int getLatestArticleId() throws RemoteException {
        return 0;
    }

    @Override
    public void addServer(int serverId) throws RemoteException {

    }

    //helper methods
	public void destroyData() {
	
		articles = new ArrayList<Article>();
		articleMap = new HashMap<Integer, List<Integer>>();

	}
	
	public boolean compareData(List<Article> articles) {
		
		if (this.articles.size() != articles.size()) return false;
		 
		for (int i = 0; i < this.articles.size(); ++i) {
			if (!this.articles.get(i).equals(articles.get(i))) {
				return false;
			}
		}

		return true;
	}

	@Override
	public List<ServerInfo> getRegisteredServers() throws RemoteException {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public String readFromServer() throws RemoteException {
		// TODO Auto-generated method stub
		return null;
	}
	
}
