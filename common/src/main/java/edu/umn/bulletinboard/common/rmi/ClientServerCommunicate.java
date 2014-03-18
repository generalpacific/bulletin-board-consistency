package edu.umn.bulletinboard.common.rmi;

import java.rmi.Remote;

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
	public int Post(String article);
	
	/**
	 * Reads the list of articles on the server. 
	 */
	public String Read();
	
	/**
	 * Returns the article corresponding to the passed id.
	 * @param id
	 * @return
	 */
	public String Choose(int id);
	
	/**
	 * Posts the reply to article with <code>id</code>.
	 * Returns the id of the new article created.
	 * 
	 * @param reply
	 * @param id
	 * @return
	 */
	public int Reply(String reply, int id);
	
}
