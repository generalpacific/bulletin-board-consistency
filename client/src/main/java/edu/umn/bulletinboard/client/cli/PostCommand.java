package edu.umn.bulletinboard.client.cli;

import java.rmi.Remote;
import java.rmi.RemoteException;

import edu.umn.bulletinboard.client.exceptions.ClientNullException;
import edu.umn.bulletinboard.common.content.Article;
import edu.umn.bulletinboard.common.rmi.BulletinBoardService;

public class PostCommand extends BaseCommand {

	private static final int ARG_ARTICLE_TEXT = 1;
	
	public PostCommand(String cmd) {
		super(cmd);
	}

	@Override
	public boolean execute(Remote client) throws NumberFormatException, RemoteException,
			ClientNullException {
		
		((BulletinBoardService) client).post(getArgument(ARG_ARTICLE_TEXT));
		return true;
	}

}
