package edu.umn.bulletinboard.server;

import java.rmi.RemoteException;
import java.util.List;

import edu.umn.bulletinboard.common.rmi.Article;
import edu.umn.bulletinboard.common.rmi.ClientServerCommunicate;

public class BulletinBoardService implements ClientServerCommunicate {
	
	private static BulletinBoardService instance = null;

	// private constructor as this class is singleton
	private BulletinBoardService() {
		if(instance != null) {
			throw new IllegalStateException("Cannot instantiate Bulletin Board twice");
		}
	}
	
	public static BulletinBoardService getInstance() {
		if(instance == null) {
			synchronized (BulletinBoardService.class) {
				if(instance == null) {
					instance = new BulletinBoardService();
				}
			}
		}
		return instance;
	}

	@Override
	public int post(Article article) throws RemoteException {
		// TODO Auto-generated method stub
		return 0;
	}

	@Override
	public List<Article> read() throws RemoteException {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public Article choose(int id) throws RemoteException {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public int reply(int id, Article reply) throws RemoteException {
		// TODO Auto-generated method stub
		return 0;
	}

}
