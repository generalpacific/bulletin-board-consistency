package edu.umn.bulletinboard.client.cli;

import java.rmi.Remote;
import java.rmi.RemoteException;

import edu.umn.bulletinboard.client.Client;
import edu.umn.bulletinboard.client.exceptions.ClientNullException;
import edu.umn.bulletinboard.common.rmi.BulletinBoardService;

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

        System.out.println("New id: " +
        Client.getInstance().getClient().post(getArgument(ARG_ARTICLE_TEXT)));
		return true;
	}

}
