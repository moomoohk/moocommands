package com.moomoohk.MooCommands.example;

import java.awt.Color;

import com.moomoohk.MooCommands.Command;
import com.moomoohk.MooCommands.CommandsManager;

/**
 * Displays help messages for registered commands.
 * 
 * This class is intended to serve as an example but is completely functional and safe to use in any program.
 * 
 * @author Meshulam Silk (moomoohk@ymail.com)
 * @since Jul 17, 2014
 */
public class HelpCommand extends Command
{
	public HelpCommand()
	{
		super();
	}

	@Override
	public boolean check(String[] params)
	{
		if (params.length > 0 && CommandsManager.findCommand(params[0]) == null)
		{
			this.outputMessage = "Command not found!";
			this.outputColor = Color.red;
			return false;
		}
		return true;
	}

	@Override
	public void execute(String[] params)
	{
		if (params.length == 0)
		{
			this.outputMessage = "---";
			for (Command c : CommandsManager.getAllCommands())
				this.outputMessage += "\n" + c.toString() + "\n---";
			return;
		}
		this.outputMessage = CommandsManager.findCommand(params[0]).toString();
	}

	@Override
	public String getCommand()
	{
		return "help";
	}

	@Override
	public String getHelpMessage()
	{
		return "Shows help";
	}

	@Override
	public String getUsage()
	{
		return "help [command]";
	}

	@Override
	public int getMaxParams()
	{
		return 1;
	}

	@Override
	public int getMinParams()
	{
		return 0;
	}
}