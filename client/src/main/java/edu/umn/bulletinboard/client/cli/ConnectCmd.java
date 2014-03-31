package edu.umn.bulletinboard.client.cli;

import edu.umn.bulletinboard.client.Client;
import edu.umn.bulletinboard.client.exceptions.ClientNullException;
import edu.umn.bulletinboard.common.constants.RMIConstants;
import edu.umn.bulletinboard.common.rmi.BulletinBoardService;
import edu.umn.bulletinboard.common.server.ServerInfo;
import edu.umn.bulletinboard.common.util.LogUtil;

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
    public boolean execute() throws NumberFormatException, RemoteException
            , ClientNullException, MalformedURLException, NotBoundException {

        startRMIClient();

        return true;
    }

    private void startRMIClient()
            throws MalformedURLException, RemoteException, NotBoundException {

        String port = "" + RMIConstants.RMI_DEFAULT_PORT;

        try {
            port = getArgument(ARG_PORT);
        } catch (IllegalArgumentException e) {
            //do nothing
        }

        LogUtil.info("rmi://"
                + getArgument(ARG_HOST) + ":" + port + "/"
                + RMIConstants.BB_SERVICE);

        BulletinBoardService client = (BulletinBoardService) Naming.lookup("rmi://"
                + getArgument(ARG_HOST) + ":" + port + "/"
                + RMIConstants.BB_SERVICE);

        Client.getInstance().setClient(client);
    }

}
