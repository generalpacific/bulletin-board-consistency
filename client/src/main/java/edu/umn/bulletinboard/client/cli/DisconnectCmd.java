package edu.umn.bulletinboard.client.cli;

import edu.umn.bulletinboard.client.exceptions.ClientNullException;

import java.net.MalformedURLException;
import java.rmi.NotBoundException;
import java.rmi.Remote;
import java.rmi.RemoteException;

/**
 *
 * Disconnect the current connection.
 *
 * Created by Abhijeet on 3/30/2014.
 */
public class DisconnectCmd extends BaseCommand {

    public DisconnectCmd(String cmd) {
        super(cmd);
    }

    @Override
    public boolean execute(Remote client) throws NumberFormatException, RemoteException
            , ClientNullException, MalformedURLException, NotBoundException {

        client = null;

        return true;
    }
}
