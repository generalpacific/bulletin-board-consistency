package edu.umn.bulletinboard.common.util;

/**
 * Consistency Type. Can be:
 * 1. Quorum based
 * 2. Sequencial
 * 3. Read-your-write 
 * 
 * @author Abhijeet
 *
 */
public enum ConsistencyType {

	
	QUORUM("Quorum"),
	SEQUENTIAL("Sequential"),
	RUW("Read-your-Write");
	
	private final String consistency;
	
	private ConsistencyType(String consistency) {
		this.consistency = consistency; 
	}
	
	/**
	 * Returns Consistency Type enum matching passed string.
	 * 
	 * @param typeStr
	 * @return
	 */
	public static ConsistencyType getType(String typeStr) {
		for(ConsistencyType value : ConsistencyType.values()) {
			if(value.toString().equals(typeStr)) {
				return value;
			}
		}
		return null;
	}
	
	@Override
	public String toString() {
		return consistency;
	}
}
