package com.moomoohk.MooCommands;

import java.awt.Color;

/**
 * This abstract class represents an executable command.
 * 
 * @author Meshulam Silk (moomoohk@ymail.com)
 * @version 2.0
 * @since 2013-11-24
 */
public abstract class Command
{
	protected String outputMessage = "";
	protected Color outputColor = null;

	/**
	 * Creates a new {@link Command} object and adds it to the {@link CommandsManager} list.
	 */
	public Command()
	{
		CommandsManager.add(this);
	}

	/**
	 * Gets this command as a String.
	 * 
	 * @return The command as a String.
	 */
	public abstract String getCommand();

	/**
	 * Gets this command's help message.
	 * 
	 * @return This commands's help message.
	 */
	public abstract String getHelpMessage();

	/**
	 * Gets the proper usage of this command.
	 * 
	 * @return The proper usage of this command.
	 */
	public abstract String getUsage();

	/**
	 * Gets the maximum number of parameters this command can be used with. -1 for infinite.
	 * 
	 * @return The maximum number of parameters this command can be used with. -1 for infinite.
	 */
	public abstract int getMaxParams();

	/**
	 * Gets the minimum number of parameters this command can be used with.
	 * 
	 * @return The minimum number of parameters this command be used with.
	 */
	public abstract int getMinParams();

	/**
	 * Gets the color that this command's output text should be printed with.
	 * <p>
	 * For example, suppose the command's output text indicates that some error has occurred, the color returned would be red. <br>
	 * This color doesn't matter unless this command's output is being printed with a colored text compatible handler.
	 * 
	 * @return The color that this command's output text should be printed with.
	 */
	public Color getOutputColor()
	{
		return this.outputColor;
	}

	/**
	 * This method will check the validity of the parameters and execute it if the check is successful.
	 * 
	 * @param params
	 *            A String array of the parameters you'd like to execute this command with.
	 */
	public String checkAndExecute(String[] params)
	{
		this.outputColor = null;
		this.outputMessage = "";
		if (check(params))
			execute(params);
		return this.outputMessage;
	}

	/**
	 * The method that checks the validity of a parameters String array for this command.<br>
	 * You should never really have to explicitly call this method. Use {@link #checkAndExecute(String[])} instead.
	 * <p>
	 * The default check method simply checks if the length of the String array of parameters falls within the bounds of {@link #getMinParams()} and {@link #getMaxParams()} of this command.
	 * 
	 * @param params
	 *            A String array of parameters.
	 * @return True if the check is successful, else false.
	 */
	protected boolean check(String[] params)
	{
		if (params.length >= getMinParams() && ((getMaxParams() >= 0) ? params.length <= getMaxParams() : true))
			return true;
		if (params.length < getMinParams())
			missingParameters(params);
		else
			if (params.length > getMaxParams())
				tooManyParameters(params);
		return false;
	}

	/**
	 * The execute code.<br>
	 * You should never really have to explicitly call this method. Use {@link #checkAndExecute(String[])} instead.
	 * <p>
	 * Simply put whatever code you'd like to be run in here, and when this command is executed the code will be run.
	 * 
	 * @param handler
	 *            The command handler you're working with.
	 * @param params
	 *            A String array of parameters.
	 */
	protected abstract void execute(String[] params);

	/**
	 * This method gets called if the check method finds that the parameter array provided contains too little parameters.
	 * <p>
	 * By default it sets this command's output message to "Missing parameters!" and this command's output color to red.
	 * 
	 * @param params
	 *            A String array of parameters.
	 */
	protected void missingParameters(String[] params)
	{
		this.outputMessage = "Missing parameters!";
		this.outputColor = Color.red;
	}

	/**
	 * This method gets called if the check method finds that the parameter array provided contains too many parameters.
	 * <p>
	 * By default it sets this command's output message to "Too many parameters!" and this command's output color to red.
	 * 
	 * @param params
	 *            A String array of parameters.
	 */
	protected void tooManyParameters(String[] params)
	{
		this.outputMessage = "Too many parameters!";
		this.outputColor = Color.red;
	}

	/**
	 * Returns a String containing information about this command using the following format:<br>
	 * [This command as a String]: [This command's help message or "<no help message specified>" if none specified], Usage: [This command's usage or "<none specified>" if none specified]
	 * 
	 * @return A String containing information about this command.
	 */
	@Override
	public String toString()
	{
		return getCommand() + ": " + (getHelpMessage().length() == 0 ? "<no help message specified>" : getHelpMessage()) + ", Usage: " + (getUsage().length() == 0 ? "<none specified>" : getUsage());
	}
}
