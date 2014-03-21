package edu.umn.bulletinboard.client.cli;

import java.rmi.Remote;
import java.rmi.RemoteException;

import edu.umn.bulletinboard.client.exceptions.ClientNullException;

/**
 * Base class for all command types. One or more RMI methods are invoked by each command.
 *  
 * @author Abhijeet
 *
 */
public abstract class BaseCommand {
	
	private String[] arguments;
	
	public BaseCommand(String cmd) {
		this.arguments = cmd.split(" ");
	}
	
	/**
	 * Execute the command using the RMI client.
	 * 
	 * @param client: RMI Client.
	 * @return boolean: true or false depending on the success or failure.
	 * @throws NumberFormatException
	 * @throws RemoteException
	 * @throws ClientNullException
	 */
	public abstract boolean execute(Remote client) 
			throws NumberFormatException, RemoteException, ClientNullException;
	
	protected String getArgument(int position) {
		
		//sanity
		if (position <= 0 || position >= arguments.length) {
			throw new IllegalArgumentException();
		}
		
		return arguments[position];
	}
	
}
