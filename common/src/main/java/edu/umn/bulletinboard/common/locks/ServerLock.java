package edu.umn.bulletinboard.common.locks;

/**
 *
 * Contains lock objects for different operations performed by servers and
 * coordinator.
 *
 * Created by Abhijeet on 3/30/2014.
 */
public class ServerLock {

    public static final Boolean register = new Boolean(true);

    public static final Boolean getID = new Boolean(true);

    /**
     * No instantiation allowed.
     */
    private ServerLock() {}

}
