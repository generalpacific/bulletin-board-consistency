package edu.umn.bulletinboard.common.rmi;

import java.rmi.RemoteException;
import java.util.List;

import edu.umn.bulletinboard.common.util.ConsistencyType;

/**
 * 
 * @author Abhijeet
 * 
 * Defines the Communication between Server and Coordinator.
 * 
 * 
 *
 */
public interface ServerCoordinatorCommunicate {

	/**
	 * Reads data item for a Server using the consistency 'type'.
	 * 
	 * @param type
	 * @return
	 * @throws RemoteException
	 */
	public List<String> readAll(ConsistencyType type) throws RemoteException;
	
	/**
	 * Reads the article that matches with specified 'id' as argument.
	 * 
	 * @param id Article id.
	 * @param type Consistency type.
	 * @return
	 * @throws RemoteException
	 */
	public boolean readS(int id, ConsistencyType type) throws RemoteException;
	
	/**
	 * 
	 * 
	 * @param type
	 * @return
	 * @throws RemoteException
	 */
	public boolean writeS(int id, ConsistencyType type) throws RemoteException;
	
	/**
	 * Get next unique Article ID. This will be unique across all the servers.
	 *  
	 * @return
	 * @throws RemoteException
	 */
	public int getNextArticleID() throws RemoteException;
	
}
