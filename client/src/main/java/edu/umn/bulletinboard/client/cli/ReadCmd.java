package edu.umn.bulletinboard.client.cli;

import edu.umn.bulletinboard.client.Client;
import edu.umn.bulletinboard.client.constants.CommandConstants;
import edu.umn.bulletinboard.client.exceptions.ClientNullException;
import edu.umn.bulletinboard.common.rmi.BulletinBoardService;
import edu.umn.bulletinboard.common.storage.MemStore;
import edu.umn.bulletinboard.common.util.IndentArticles;
import edu.umn.bulletinboard.common.util.LogUtil;

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

        if (null == cli.getClient()) {
            throw new RemoteException(CommandConstants.ERR_CLIENT_NULL);
        }

        cli.setRead(true);

        if (cli.isRYWSet() && MemStore.getInstance().getAllArticles().size() > 0) {
            LogUtil.log ("read()", "Retrieving articles from cache.");
            System.out.println(IndentArticles.getArticlesStr(MemStore.getInstance()
                    .getAllArticles()));
            return true;
        }

        LogUtil.log ("read()", "Retrieving articles from server.");
        System.out.println(cli.getClient().read());

        return true;
    }
}
