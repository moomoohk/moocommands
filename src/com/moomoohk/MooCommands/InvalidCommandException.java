package com.moomoohk.MooCommands;

/**
 * Thrown when an invalid {@link Command} is created.
 * 
 * @author Meshulam Silk (moomoohk@ymail.com)
 * @version 1.0
 * @since 2013-11-24
 */
public class InvalidCommandException extends Exception
{
	private static final long serialVersionUID = -1529318301670175287L;

	/**
	 * Constructs an {@link InvalidCommandException} with the specified detail message.
	 * 
	 * @param message
	 *            The detail message.
	 */
	public InvalidCommandException(String message)
	{
		super(message);
	}
}
