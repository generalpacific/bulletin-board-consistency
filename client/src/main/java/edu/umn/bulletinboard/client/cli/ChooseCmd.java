package edu.umn.bulletinboard.client.cli;

import edu.umn.bulletinboard.client.Client;
import edu.umn.bulletinboard.client.exceptions.ClientNullException;
import edu.umn.bulletinboard.common.rmi.BulletinBoardService;
import edu.umn.bulletinboard.common.storage.MemStore;

import java.net.MalformedURLException;
import java.rmi.NotBoundException;
import java.rmi.Remote;
import java.rmi.RemoteException;

/**
 *
 * Passes a chosen ID that should be retrieved.
 *
 * Created by Abhijeet on 3/30/2014.
 */
public class ChooseCmd extends BaseCommand {

    private static final int ARG_ARTICLE_ID = 1;

    public ChooseCmd(String cmd) {
        super(cmd);
    }

    @Override
    public boolean execute() throws NumberFormatException, RemoteException
            , ClientNullException, MalformedURLException, NotBoundException {

        Client cli = Client.getInstance();
        if (!cli.isReadSet()) {
            throw new RemoteException("Read should be called first.");
        }

        int id = Integer.parseInt(getArgument(ARG_ARTICLE_ID));

        if (cli.isRYWSet() && MemStore.getInstance().getArticle(id) != null) {
            System.out.println(MemStore.getInstance().getArticle(id));
            return true;
        }

        System.out.println(Client.getInstance().getClient().choose(id));

        return true;
    }
}
