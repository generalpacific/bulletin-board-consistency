package edu.umn.bulletinboard.client.cli;

import edu.umn.bulletinboard.client.exceptions.ClientNullException;
import edu.umn.bulletinboard.common.constants.RMIConstants;
import edu.umn.bulletinboard.common.rmi.BulletinBoardService;
import edu.umn.bulletinboard.common.server.ServerInfo;

import java.net.MalformedURLException;
import java.rmi.Naming;
import java.rmi.NotBoundException;
import java.rmi.Remote;
import java.rmi.RemoteException;

/**
 *
 * Connects to a remote client.
 *
 * Created by Abhijeet on 3/30/2014.
 */
public class ConnectCmd extends BaseCommand {

    private static final int ARG_HOST = 1;
    private static final int ARG_PORT = 2;

    public ConnectCmd(String cmd) {
        super(cmd);
    }

    @Override
    public boolean execute(Remote client) throws NumberFormatException, RemoteException
            , ClientNullException, MalformedURLException, NotBoundException {

        startRMIClient(client);

        return true;
    }

    private void startRMIClient(Remote client)
            throws MalformedURLException, RemoteException, NotBoundException {
        client = (BulletinBoardService) Naming.lookup("rmi://"
                + getArgument(ARG_HOST) + ":" + getArgument(ARG_HOST) + "/"
                + RMIConstants.BB_SERVICE);
    }

}
