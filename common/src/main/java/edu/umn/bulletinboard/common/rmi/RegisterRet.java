package edu.umn.bulletinboard.common.rmi;

import java.io.Serializable;
import java.rmi.RemoteException;
import java.util.ArrayList;
import java.util.List;

import edu.umn.bulletinboard.common.server.ServerInfo;

/**
 *
 * The return type of Register coordinator call.
 *
 * Created by Abhijeet on 3/30/2014.
 */
public class RegisterRet implements Serializable{

    /**
	 * 
	 */
	private static final long serialVersionUID = -2798377558923822865L;
	int id;
    List<ServerInfo> servers = null;

    public RegisterRet(int id, List<ServerInfo> servers) throws RemoteException{
        this.id = id;
        this.servers = new ArrayList<ServerInfo>(servers);
    }

    public int getId() {
        return id;
    }

    public List<ServerInfo> getServers() {
        return servers;
    }
}
