package edu.umn.bulletinboard.server;

import edu.umn.bulletinboard.common.content.Article;
import edu.umn.bulletinboard.common.rmi.BulletinBoardService;
import edu.umn.bulletinboard.common.util.ConsistencyType;

import java.rmi.RemoteException;
import java.util.List;

/**
 * Created by Abhijeet on 3/29/2014.
 *
 *
 */
public class BulletinBoardServiceImpl implements BulletinBoardService{

    private static BulletinBoardServiceImpl bb = null;

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
        return 0;
    }

    @Override
    public String read() throws RemoteException {
        return null;
    }

    @Override
    public Article choose(int id) throws RemoteException {
        return null;
    }

    @Override
    public int reply(int id, Article reply) throws RemoteException {
        return 0;
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
    public int register() throws RemoteException {
        return 0;
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
}
