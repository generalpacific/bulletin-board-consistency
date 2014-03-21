package edu.umn.bulletinboard.client.cli;

import edu.umn.bulletinboard.client.constants.CommandConstants;
import edu.umn.bulletinboard.client.exceptions.IllegalCommandException;
import edu.umn.bulletinboard.common.util.StringUtil;

/**
 * Factory that returns appropriate Command Handler to handle
 * the command sent by User.
 * 
 * @author Abhijeet
 *
 */
public class CommandFactory {

	private static final String POST_PREFIX = "post";
	private static final String READ_PREFIX = "read";
	private static final String CHOOSE_PREFIX = "choose"; 
	private static final String REPLY_PREFIX = "reply";
		
	public static BaseCommand getCommand(String cmd) 
			throws IllegalCommandException {
		
		String prefix = StringUtil.getCmdPrefix(cmd).trim();

		if (prefix.equalsIgnoreCase(POST_PREFIX)) {
			return new PostCommand(cmd);
		} else if (prefix.equalsIgnoreCase(READ_PREFIX)) {
			//TODO
		} else if (prefix.equalsIgnoreCase(CHOOSE_PREFIX)) {
			//TODO
		} else if (prefix.equalsIgnoreCase(REPLY_PREFIX)) {
			//TODO
		}
		
		throw new IllegalCommandException(prefix + " " 
				+ CommandConstants.ERR_COMMAND_NOT_FOUND);
	}
}
