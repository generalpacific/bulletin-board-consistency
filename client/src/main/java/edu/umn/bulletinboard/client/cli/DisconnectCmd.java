package edu.umn.bulletinboard.client.cli;

import edu.umn.bulletinboard.client.Client;
import edu.umn.bulletinboard.client.constants.CommandConstants;
import edu.umn.bulletinboard.client.exceptions.ClientNullException;
import edu.umn.bulletinboard.common.storage.MemStore;
import edu.umn.bulletinboard.common.util.LogUtil;

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
    public boolean execute() throws NumberFormatException, RemoteException
            , ClientNullException, MalformedURLException, NotBoundException {

        Client cli = Client.getInstance();

        LogUtil.log("disconnect()", "Disconnecting.");

        if (null == cli.getClient()) {
            throw new RemoteException(CommandConstants.ERR_CLIENT_NULL);
        }

        cli.setClient(null);
        cli.setRead(false);
        return true;
    }
}
