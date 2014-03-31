package edu.umn.bulletinboard.client.cli;

import edu.umn.bulletinboard.client.Client;
import edu.umn.bulletinboard.client.constants.CommandConstants;
import edu.umn.bulletinboard.client.exceptions.ClientNullException;
import edu.umn.bulletinboard.common.content.Article;
import edu.umn.bulletinboard.common.exception.InvalidArticleException;
import edu.umn.bulletinboard.common.rmi.BulletinBoardService;
import edu.umn.bulletinboard.common.storage.MemStore;
import edu.umn.bulletinboard.common.util.LogUtil;

import java.net.MalformedURLException;
import java.rmi.NotBoundException;
import java.rmi.Remote;
import java.rmi.RemoteException;

/**
 *
 * Reply comment to an existing article.
 *
 * Created by Abhijeet on 3/30/2014.
 */
public class ReplyCmd extends BaseCommand {

    private static final int ARG_ID = 1;
    private static final int ARG_ARTICLE_TEXT = 2;

    public ReplyCmd(String cmd) {
        super(cmd);
    }

    @Override
    public boolean execute() throws NumberFormatException, RemoteException
            , ClientNullException, MalformedURLException, NotBoundException {

        Client cli = Client.getInstance();

        if (null == cli.getClient()) {
            throw new RemoteException(CommandConstants.ERR_CLIENT_NULL);
        }

        int pId = Integer.parseInt(getArgument(ARG_ID));

        int id = Client.getInstance().getClient().reply(pId
                , new Article(-1, getArgument(ARG_ARTICLE_TEXT)));
        LogUtil.log("post()", "New id posted: " + id);

        if (cli.isRYWSet()) {
            try {
                MemStore.getInstance().getArticle(pId).addReplies(pId);
                MemStore.getInstance().addArticle(new Article(id, getArgument(ARG_ARTICLE_TEXT)));
                LogUtil.log("post()", "Data posted inc cache.");
            } catch (InvalidArticleException e) {
                throw new RemoteException(e.getMessage());
            }
        }


        return true;
    }
}
