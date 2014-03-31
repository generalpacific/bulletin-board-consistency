package edu.umn.bulletinboard.common.constants;

public final class PropertiesConstants {

	private PropertiesConstants() {
		throw new IllegalStateException("Cannot instantiate this class.");
	}
	
	public static final String NR = "NR";
	public static final String NW = "NW";
	public static final String COORDINATING_SERVER_IP = "coordinatingServerIp";
	public static final String COORDINATING_SERVER_RMI_PORT = "coordinatingServerRMIPort";
	public static final String CONSISTENCY_TYPE = "consistencyType";
}
