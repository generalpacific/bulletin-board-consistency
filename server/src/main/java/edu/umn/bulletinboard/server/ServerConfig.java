package edu.umn.bulletinboard.server;

import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.io.InputStream;
import java.util.Properties;

import edu.umn.bulletinboard.common.constants.PropertiesConstants;
import edu.umn.bulletinboard.common.util.ConsistencyType;
import edu.umn.bulletinboard.common.util.LogUtil;
import edu.umn.bulletinboard.common.validator.ContentValidator;

/**
 * This file contains the server config which is initialized from the property file.
 * @author prashant
 *
 */
public class ServerConfig {
	private static final String CLASS_NAME = ServerConfig.class.getSimpleName();
	
	private ServerConfig() {
		throw new IllegalStateException("Config class cannot instantiate");
	}
	
	private static String coordinatingServerIp = null;
	

	private static int coordinatingServerRMIPort = -1;
	private static boolean isCoordinatingServer = false;
	private static ConsistencyType consistencyType = null;
	private static int NR = -1;
	private static int NW = -1;
	
	public static void loadProperties(String filename) {
		final String method = CLASS_NAME + ".loadProperties()"; 
		//InputStream input = Thread.currentThread().getContextClassLoader().getResourceAsStream("./" + filename);
		InputStream input = null;
		try {
			input = new FileInputStream(filename);
		} catch (FileNotFoundException e1) {
			// TODO Auto-generated catch block
			e1.printStackTrace();
		}
		if(input == null){
	        LogUtil.log(method,"Sorry, unable to find " + filename + ". Exiting.");
		    System.exit(1);
		}
		Properties prop = new Properties();
		try {
			prop.load(input);
		} catch (IOException e) {
			LogUtil.log(method,"Cannot load the properties file. Exiting.");
		    System.exit(1);
		}
		
		// Get the coordinating server ip and port
		// If both are null then this is a coordinating server.
		coordinatingServerIp = prop.getProperty(PropertiesConstants.COORDINATING_SERVER_IP);
		if(null == coordinatingServerIp) {
			isCoordinatingServer = true;
		}
		if(ContentValidator.isValidPort(prop.getProperty(PropertiesConstants.COORDINATING_SERVER_RMI_PORT))) {
			coordinatingServerRMIPort = Integer.parseInt(
					prop.getProperty(PropertiesConstants.COORDINATING_SERVER_RMI_PORT));
		}else{
			if(!isCoordinatingServer) {
				LogUtil.log(method, "Invalid RMI port: " + prop.getProperty(PropertiesConstants.COORDINATING_SERVER_RMI_PORT));	
			}
		}
		
		ConsistencyType tempType = ConsistencyType.getType(prop.getProperty(PropertiesConstants.CONSISTENCY_TYPE));
		if(tempType == null) {
			LogUtil.log(method, "Invalid Consistency Type. Exiting.");
			System.exit(1);
		}
		consistencyType = tempType;
		
		if(consistencyType.equals(ConsistencyType.QUORUM)) {
			String temp = prop.getProperty(PropertiesConstants.NR);
			if(ContentValidator.isValidPort(temp)) {
				NR = Integer.parseInt(temp);
			}else{
				LogUtil.log(method, "Invalid value for NR : " + temp);
				System.exit(1);
			}
			temp = prop.getProperty(PropertiesConstants.NW);
			if(ContentValidator.isValidPort(temp)) {
				NW = Integer.parseInt(temp);
			}else{
				LogUtil.log(method, "Invalid value for NW : " + temp);
				System.exit(1);
			}
		}
		
	}

	public static int getCoordinatingServerRMIPort() {
		return coordinatingServerRMIPort;
	}

	public static boolean isCoordinatingServer() {
		return isCoordinatingServer;
	}

	public static ConsistencyType getConsistencyType() {
		return consistencyType;
	}

	public static int getNR() {
		return NR;
	}

	public static int getNW() {
		return NW;
	}

	public static String getCoordinatingServerIp() {
		return coordinatingServerIp;
	}
}
