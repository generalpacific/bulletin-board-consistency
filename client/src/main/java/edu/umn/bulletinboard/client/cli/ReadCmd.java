package edu.umn.bulletinboard.client.cli;

import edu.umn.bulletinboard.client.Client;
import edu.umn.bulletinboard.client.exceptions.ClientNullException;
import edu.umn.bulletinboard.common.rmi.BulletinBoardService;

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
    public boolean execute() throws NumberFormatException, RemoteException, ClientNullException, MalformedURLException, NotBoundException {

        System.out.println(
                Client.getInstance().getClient().read());

        return true;
    }
}
