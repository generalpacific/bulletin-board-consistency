package edu.umn.bulletinboard.client.cli;

import java.rmi.Remote;
import java.rmi.RemoteException;

import edu.umn.bulletinboard.client.Client;
import edu.umn.bulletinboard.client.constants.CommandConstants;
import edu.umn.bulletinboard.client.exceptions.ClientNullException;
import edu.umn.bulletinboard.common.content.Article;
import edu.umn.bulletinboard.common.exception.InvalidArticleException;
import edu.umn.bulletinboard.common.rmi.BulletinBoardService;
import edu.umn.bulletinboard.common.storage.MemStore;
import edu.umn.bulletinboard.common.util.LogUtil;

/**
 * Post a new article.
 *
 * @author Abhijeet
 */
public class PostCommand extends BaseCommand {

	private static final int ARG_ARTICLE_TEXT = 1;
	
	public PostCommand(String cmd) {
		super(cmd);
	}

	@Override
	public boolean execute() throws NumberFormatException, RemoteException,
			ClientNullException {

        Client cli = Client.getInstance();

        if (null == cli.getClient()) {
            throw new RemoteException(CommandConstants.ERR_CLIENT_NULL);
        }


        int id = cli.getClient().post(getArgument(ARG_ARTICLE_TEXT));
        System.out.println("New id: " + id);

        if (cli.isRYWSet()) {
            try {
                MemStore.getInstance().addArticle(new Article(id, getArgument(ARG_ARTICLE_TEXT)));
            } catch (InvalidArticleException e) {
                throw new RemoteException(e.getMessage());
            }
        }

		return true;
	}

}
