package edu.umn.bulletinboard.client;

import java.net.MalformedURLException;
import java.rmi.NotBoundException;
import java.rmi.RemoteException;
import java.util.ArrayList;
import java.util.List;

import static org.junit.Assert.*;

import org.junit.Before;
import org.junit.Test;

import edu.umn.bulletinboard.client.exceptions.ClientNullException;
import edu.umn.bulletinboard.common.content.Article;

/**
 * Tests shell client. Mocks RMI Server.
 * 
 * @author Abhijeet
 *
 */
public class TestClient {
	
	MCServer server;
	Client cli;
	int counter = 0;
	List<Article> arti = new ArrayList<Article>();

	@Before
	public void setUp() {
		server = new MCServer();
		try {
			cli = new Client(server);
		} catch (MalformedURLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} catch (RemoteException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} catch (NotBoundException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}
	
	@Test
	public void testpost() {
		
		try {
			for (int i = 0; i < 4; ++i) {
				cli.executeCmd(prepPostArgs(++counter));
				Article e = new Article(counter, "Article"+counter);
				arti.add(e);
			}
			
			assertTrue(server.compareData(arti));
			
		} catch (NumberFormatException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} catch (ClientNullException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}
	
	public String prepPostArgs(int id) {
		return "post Article" + id;
	}
	
}
