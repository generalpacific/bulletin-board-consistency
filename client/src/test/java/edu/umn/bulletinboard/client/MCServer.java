package edu.umn.bulletinboard.client;

import java.rmi.RemoteException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import edu.umn.bulletinboard.common.rmi.Article;
import edu.umn.bulletinboard.common.rmi.ClientServerCommunicate;

/**
 * Server Mocked to test client only.
 * 
 * @author Abhijeet
 *
 */
public class MCServer implements ClientServerCommunicate {
	
	/**
	 * This is an append only list, items will never be
	 * removed from this. So,
	 */
	private List<Article> articles = new ArrayList<Article>();
	
	private Map<Integer, List<Integer>> articleMap = new HashMap<Integer, List<Integer>>();
	private int counter = 0;
	

	@Override
	public int post(Article article) throws RemoteException {
		
		article.setId(++counter);
		articles.add(article);
		
		return counter;
	}

	@Override
	public List<Article> read() throws RemoteException {
		return articles;
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
		int newId = post(reply);
		
		if (articleMap.get(id) == null) {
			List<Integer> list = new ArrayList<Integer>();
			list.add(newId);
			articleMap.put(id, list);
		} else {
			articleMap.get(id).add(newId);
		}
		
		return newId;
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
	
}
