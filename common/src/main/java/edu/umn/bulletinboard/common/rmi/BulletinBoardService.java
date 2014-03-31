package edu.umn.bulletinboard.common.rmi;

import java.rmi.Remote;
import java.rmi.RemoteException;
import java.util.List;

import edu.umn.bulletinboard.common.content.Article;
import edu.umn.bulletinboard.common.content.RegisterRet;
import edu.umn.bulletinboard.common.server.ServerInfo;
import edu.umn.bulletinboard.common.util.ConsistencyType;

/**
 * RMI interface to be implemented by each server for the communication between 
 * client-server, server-server and server-coordinatingServer. <br><br>
 * Supports following operations for client-server:<br>
 * <ul>
 * 		<li> Post </li>
 * 		<li> Read </li>
 * 		<li> Choose </li>
 * 		<li> Reply </li>
 * </ul>
 * 
 * Supports following operations for server-coordinatingServer:<br>
 * <ul>
 * 		<li> readFromCoordinatingServer </li>
 * 		<li> chooseFromCoordinatingServer </li>
 * 		<li> writeToCoordinatingServer </li>
 * 		<li> replyToCoordinatingServer </li>
 * 		<li> getNextArticleID </li>
 * </ul>
 * 
 * Supports following operations for the coordinatingServer-server communication: <br>
 * <ul>
 * 		<li> sync </li>
 * 		<li> writeToServer </li>
 * 		<li> readFromServer </li>
 * 		<li> getLatestArticleID </li>
 * 		<li> register </li>
 *  	<li> addServer </li>
 * </ul>
 * 
 * @author prashant
 *
 */
public interface BulletinBoardService extends Remote {
	
	/**
	 * Post an article to the server. This method will return the id of the 
	 * new article.
	 * 
	 * @param article
	 * @return
	 */
	public int post(String article) throws RemoteException;
	
	/**
	 * Reads the list of articles on the server.
	 * Returns the formatted string of articles which can be displayed by the client. 
	 */
	public String read() throws RemoteException;
	
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
	
	/**
	 * Reads data item for a Server using the consistency <code>type</code>.
	 * 
	 * @param type
	 * @return List<Article>
	 * @throws RemoteException
	 */
	public List<Article> readFromCoordinatingServer(ConsistencyType type) throws RemoteException;
	
	/**
	 * Reads the article that matches with specified <code>id</code> as argument.
	 * 
	 * @param id Article id.
	 * @param type Consistency type.
	 * @return Article
	 * @throws RemoteException
	 */
	public Article chooseFromCoordinatingServer(int id, ConsistencyType type) throws RemoteException;
	
	/**
	 * Posts a new article <code>articleText</code> with consistency 
	 * <code>type</code>
	 * @param type
	 * @return
	 * @throws RemoteException
	 */
	public int writeToCoordinatingServer(Article articleText, ConsistencyType type) throws RemoteException;
	
	/**
	 * Replies to new article <code>articleID</code> with consistency 
	 * <code>type</code>
	 * @param type
	 * @return
	 * @throws RemoteException
	 */
	public int replyToCoordinatingServer(int articleId, ConsistencyType type) throws RemoteException;
	
	/**
	 * Get next unique Article ID. This will be unique across all the servers.
	 *  
	 * @return article id
	 * @throws RemoteException
	 */
	public int getNextArticleID() throws RemoteException;
	
	/**
	 * Registers the current server to the coordinating server.
	 * Returns the server id for the server.  
	 */
	public RegisterRet register(String ip, int port) throws RemoteException;
	
	/**
	 * Returns the list of registered servers.
	 * @return
	 * @throws RemoteException
	 */
	public List<ServerInfo> getRegisteredServers() throws RemoteException;
	
	/**
	 * Sync the articles on the server with the passed articles.
	 *  
	 * @throws RemoteException
	 */
	public void sync(List<Article> articles) throws RemoteException;
	
	/**
	 * Writes the current article to the server.
	 *  
	 * @throws RemoteException
	 */
	public void writeToServer(int articleId, String articleText) throws RemoteException;
	
	/**
	 * Read article from server with the given articleId.
	 *  
	 * @throws RemoteException
	 */
	public String readFromServer(int articleId) throws RemoteException;
	
	/**
	 * Read articles from the server.
	 *  
	 * @throws RemoteException
	 */
	public String readFromServer() throws RemoteException;
	
	/**
	 * Get the latest article id from the server.
	 *  
	 * @return latest article id
	 * @throws RemoteException
	 */
	public int getLatestArticleId() throws RemoteException;
	
	/**
	 * Adds the new serverid to the server.
	 *  
	 * @return latest article id
	 * @throws RemoteException
	 */
	public void addServer(int serverId) throws RemoteException;
}
