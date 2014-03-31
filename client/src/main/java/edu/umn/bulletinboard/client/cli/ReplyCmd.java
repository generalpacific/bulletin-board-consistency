package edu.umn.bulletinboard.client.cli;

import edu.umn.bulletinboard.client.Client;
import edu.umn.bulletinboard.client.exceptions.ClientNullException;
import edu.umn.bulletinboard.common.content.Article;
import edu.umn.bulletinboard.common.rmi.BulletinBoardService;

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

        System.out.println("New id: " +
        Client.getInstance().getClient().reply(Integer.parseInt(getArgument(ARG_ID))
                , new Article(-1, getArgument(ARG_ARTICLE_TEXT))));

        return true;
    }
}
