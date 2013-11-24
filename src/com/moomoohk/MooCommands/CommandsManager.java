package com.moomoohk.MooCommands;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.Scanner;

/**
 * This class manages all the {@link Command}s created.
 * 
 * @author Meshulam Silk <moomoohk@ymail.com>
 * @version 1.0
 * @since 2013-11-24
 */
public final class CommandsManager
{
	private static ArrayList<Command> commands = new ArrayList<Command>();

	/**
	 * Adds a {@link Command} to the commands list. <br>
	 * This method should never be called explicitly.<br>
	 * Commands will call this method from their constructor.
	 * 
	 * @param command
	 *            Command to add to the list.
	 */
	public static void add(Command command)
	{
		if (!commands.contains(command))
			commands.add(command);
	}

	/**
	 * Finds a {@link Command} in the commands list.
	 * 
	 * @param command
	 *            The {@link Command} to find in String form.
	 *            <p>
	 *            This String either the Command itself or the command and some parameters.
	 * @return The {@link Command} corresponding to the String sent. Null if not found.
	 */
	public static Command findCommand(String command)
	{
		if (command.trim().contains(" "))
			command = command.trim().substring(0, command.trim().indexOf(" "));
		for (Command temp : commands)
			if (temp.getCommand().equals(command.trim()))
				return temp;
		return null;
	}

	/**
	 * Gets the commands list.
	 * 
	 * @return The commands list.
	 */
	public static ArrayList<Command> getAllCommands()
	{
		return commands;
	}

	/**
	 * Executes a {@link Command} in the commands list.
	 * 
	 * @param commandAndParams
	 *            The command in String form and any parameters to execute with.<br>
	 *            Theoretically this can be raw user input.
	 * @return The output message of the {@link Command} executed.
	 * @throws InvalidCommandException
	 *             If the input String's length is 0 or if the command cannot be found.
	 */
	public static String executeCommand(String commandAndParams) throws InvalidCommandException
	{
		if (commandAndParams.trim().length() == 0)
			throw new InvalidCommandException("Input length == 0");
		if (findCommand(commandAndParams) == null)
			throw new InvalidCommandException("Command not found");
		return findCommand(commandAndParams).checkAndExecute(parseParams(commandAndParams));
	}

	/**
	 * Will parse the parameters from an input String into a String array.<br>
	 * For example: "/test param1 param2" will return {"param1", "param2"}.
	 * 
	 * @param command
	 *            The input to parse.
	 * @return The parsed parameters.
	 * @throws InvalidCommandException
	 *             If the input String does not contain a command and parameters.
	 */
	public static String[] parseParams(String params)
	{
		if (!params.trim().contains(" "))
			return new String[0];
		params = params.trim().substring(params.trim().indexOf(" "));
		int count = params.trim().length() - params.trim().replace(" ", "").length() + 1;
		String[] newParams = new String[count];
		Scanner scanner = new Scanner(params);
		int i = 0;
		while (scanner.hasNext())
			newParams[i++] = scanner.next();
		return newParams;
	}

	/**
	 * Receives a String array and combines all the indexes into one String object.
	 * 
	 * @param params
	 *            A String array of parameters.
	 * @param start
	 *            The index with which to start combining.
	 * @return A String object which contains all the parameters combined starting at the specified index.
	 */
	public static String stringParams(String[] params, int start)
	{
		if (params.length == 0 || start >= params.length)
			return null;
		String temp = params[start];
		for (int i = start + 1; i < params.length; i++)
			temp += " " + params[i];
		return temp;
	}

	/**
	 * This method receives a String array of parameters which contain flags (which are formatted "flag:value") and will create a HashMap<String, String> which contains the flags as keys and their values.
	 * 
	 * @param params
	 *            String array of parameters where each place contains a flag.
	 * @return A HashMap<String, String> which contains the flags and their values.
	 */
	public static HashMap<String, String> parseFlags(String[] params)
	{
		HashMap<String, String> flags = new HashMap<String, String>();
		for (String param : params)
		{
			Scanner sc = new Scanner(param);
			sc.useDelimiter(":");
			try
			{
				flags.put(sc.next().trim(), sc.next().trim());
			}
			catch (Exception e)
			{
				throw new IllegalStateException("Incorrect syntax!");
			}
		}
		return flags;
	}
}
