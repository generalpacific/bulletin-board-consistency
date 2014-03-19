package edu.umn.bulletinboard.common.rmi;

import java.rmi.RemoteException;
import java.util.List;

import edu.umn.bulletinboard.common.util.ConsistencyType;

/**
 * Defines the Communication between Server and Coordinator.
 * 
 * @author Abhijeet
 * 
 */
public interface ServerCoordinatorCommunicate {

	/**
	 * Reads data item for a Server using the consistency <code>type</code>.
	 * 
	 * @param type
	 * @return List<Article>
	 * @throws RemoteException
	 */
	public List<Article> readForServer(ConsistencyType type) throws RemoteException;
	
	/**
	 * Reads the article that matches with specified <code>id</code> as argument.
	 * 
	 * @param id Article id.
	 * @param type Consistency type.
	 * @return Article
	 * @throws RemoteException
	 */
	public Article chooseForServer(int id, ConsistencyType type) throws RemoteException;
	
	/**
	 * Posts a new article <code>articleText</code> with consistency 
	 * <code>type</code>
	 * @param type
	 * @return
	 * @throws RemoteException
	 */
	public int writeForServer(Article articleText, ConsistencyType type) throws RemoteException;
	
	/**
	 * Get next unique Article ID. This will be unique across all the servers.
	 *  
	 * @return article id
	 * @throws RemoteException
	 */
	public int getNextArticleID() throws RemoteException;
	
}
