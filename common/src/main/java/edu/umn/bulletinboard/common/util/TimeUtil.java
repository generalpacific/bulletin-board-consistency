package edu.umn.bulletinboard.common.util;

import java.util.Random;

public final class TimeUtil {
	
	private static final int LOWER_LIMIT = 200;
	private static final int UPPER_LIMIT = 500;
	
	private TimeUtil() {
		throw new IllegalStateException("Util class cannot be instantiated.");
	}
	
	/**
	 * Returns random delay between 500-2000ms
	 * @return
	 */
	public static long getDelay() {
		return (long)(new Random().nextInt(UPPER_LIMIT - LOWER_LIMIT) + LOWER_LIMIT);
	}
	
	public static void delay() {
		try {
			Thread.sleep(getDelay());
		} catch (InterruptedException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}
}
