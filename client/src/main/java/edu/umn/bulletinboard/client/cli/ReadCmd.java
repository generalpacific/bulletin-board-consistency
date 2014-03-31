package edu.umn.bulletinboard.client.cli;

import edu.umn.bulletinboard.client.Client;
import edu.umn.bulletinboard.client.exceptions.ClientNullException;
import edu.umn.bulletinboard.common.rmi.BulletinBoardService;
import edu.umn.bulletinboard.common.storage.MemStore;
import edu.umn.bulletinboard.common.util.IndentArticles;

import java.net.MalformedURLException;
import java.rmi.NotBoundException;
import java.rmi.Remote;
import java.rmi.RemoteException;

/**
 * Created by Abhijeet on 3/30/2014.
 */
public class ReadCmd extends BaseCommand {

    public ReadCmd(String cmd) {
        super(cmd);
    }

    @Override
    public boolean execute() throws NumberFormatException, RemoteException
            , ClientNullException, MalformedURLException, NotBoundException {

        Client cli = Client.getInstance();
        cli.setRead(true);

        if (cli.isRYWSet() && MemStore.getInstance().getAllArticles().size() > 0) {
            System.out.println(IndentArticles.getArticlesStr(MemStore.getInstance()
                    .getAllArticles()));
            return true;
        }

        System.out.println(cli.getClient().read());

        return true;
    }
}
