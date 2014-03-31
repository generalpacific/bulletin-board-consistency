package edu.umn.bulletinboard.common.content;

import edu.umn.bulletinboard.common.server.ServerInfo;

import java.util.ArrayList;
import java.util.List;

/**
 *
 * The return type of Register coordinator call.
 *
 * Created by Abhijeet on 3/30/2014.
 */
public class RegisterRet {

    int id;
    List<ServerInfo> servers = null;

    public RegisterRet(int id, List<ServerInfo> servers) {
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
