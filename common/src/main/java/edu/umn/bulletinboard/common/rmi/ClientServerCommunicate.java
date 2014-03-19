package edu.umn.bulletinboard.common.rmi;

import java.rmi.Remote;
import java.rmi.RemoteException;
import java.util.List;

/**
 * RMI interface to be implemented by each server for the communication between 
 * client and server. <br><br>
 * Supports following operations:<br>
 * <ul>
 * 		<li> Post </li>
 * 		<li> Read </li>
 * 		<li> Choose </li>
 * 		<li> Reply </li>
 * </ul>
 * @author prashant
 *
 */
public interface ClientServerCommunicate extends Remote{
	
	/**
	 * Post an article to the server. This method will return the id of the 
	 * new article.
	 * 
	 * @param article
	 * @return
	 */
	public int post(Article article) throws RemoteException;
	
	/**
	 * Reads the list of articles on the server. 
	 */
	public List<Article> read() throws RemoteException;
	
	/**
	 * Returns the article corresponding to the passed id.
	 * @param id
	 * @return
	 */
	public Article choose(int id) throws RemoteException;
	
	/**
	 * Posts the reply to article with <code>id</code>.
	 * Returns the id of the new article created.
	 * 
	 * @param reply
	 * @return
	 */
	public int reply(int id, Article reply) throws RemoteException;
	
}
