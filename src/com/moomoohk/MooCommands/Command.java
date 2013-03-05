package com.moomoohk.MooCommands;

import java.util.ArrayList;

public abstract class Command<T>
{
	public static ArrayList<Command<?>> commands = new ArrayList<Command<?>>();
	protected String command, message, help, color = null;
	protected int minParams, maxParams;
	protected boolean containsHTML;
	protected T handler;

	public Command(T handler, String command, String help, int minParams, int maxParams)
	{
		this.handler=handler;
		this.command = command;
		this.help = help;
		this.containsHTML = false;
		this.minParams = minParams;
		this.maxParams = maxParams;
	}
	public static void add(Command<?> command)
	{
		if (getCommand(command.getCommand()) == null)
			commands.add(command);
		else
			throw new IllegalStateException(command.getCommand() + " already exists in the list!");
	}

	public String getMessage()
	{
		return message;
	}

	public String getHelp()
	{
		return help;
	}

	public String getCommand()
	{
		return command;
	}

	public String getColor()
	{
		return this.color;
	}

	public boolean containsHTML()
	{
		return this.containsHTML;
	}

	public static String getAllHelp(boolean HTML)
	{
		if (commands.size() == 0)
			return "No commands";
		if(HTML)
		{
			String help = "<b><u>Commands:</u></b><br>";
			for (int i = 0; i < commands.size(); i++)
			{
				help += "&nbsp;&nbsp;- - -<br>";
				help += "&nbsp;&nbsp;" + commands.get(i).getCommand() + "<br>";
				help += "&nbsp;&nbsp;" + commands.get(i).getHelp() + "<br>";
			}
			help += "&nbsp;&nbsp;- - -";
			return help;
		}
		String help="Commands:\n";
		for (int i = 0; i < commands.size(); i++)
		{
			help += "- - -\n";
			help +=  commands.get(i).getCommand() + "\n";
			help +=  commands.get(i).getHelp() + "\n";
		}
		help += "- - -";
		return help;
	}

	public static String stringParams(String[] params, int start)
	{
		if (params.length == 0 || start >= params.length)
			return null;
		String temp = params[start];
		for (int i = start+1; i < params.length; i++)
			temp += " "+params[i];
		return temp;
	}

	public static String parseCommand(String command)
	{
		if (command.trim().contains(" "))
			return command.trim().substring(command.indexOf("/"), command.indexOf(" "));
		return command;
	}

	public static String[] parseParams(String command)
	{
		command = command.trim();
		int count = 0;
		for (int i = 0; i < command.length(); i++)
			if (command.charAt(i) == ' ')
			{
				count++;
				while (i < command.length() && command.charAt(i) == ' ')
					i++;
			}
		if (count == 0)
			return new String[]{};
		command += " ";
		String[] params = new String[0];
		String temp = null;
		for (int i = 0; i < command.length(); i++)
		{
			if (command.charAt(i) == ' ')
			{
				if (temp != null)
				{
					String[] temp2 = new String[params.length + 1];
					for (int j = 0; j < params.length; j++)
						temp2[j] = params[j];
					temp2[temp2.length - 1] = temp;
					params = temp2;
				}
				temp = null;
				continue;
			}
			if (temp == null)
				temp = "";
			temp += command.charAt(i);
		}
		String[] temp2 = new String[params.length - 1];
		for (int i = 1; i < params.length; i++)
			temp2[i - 1] = params[i];
		params = temp2;
		return params;
	}

	public static Command<?> getCommand(String command)
	{
		command=command.charAt(0)=='/'?command:"/"+command;
		for (Command<?> temp : commands)
			if (temp.getCommand().equals(command))
				return temp;
		return null;
	}

	public String toString()
	{
		return "Command: " + this.command + "\nMessage: " + this.message + "\nHelp: " + this.help;
	}

	public void checkAndExecute(String[] params)
	{
		try
		{
			if (check(this.handler, params))
				execute(this.handler, params);
		}
		catch (Exception e)
		{
			this.message = "[COMMAND ERROR] Problem with check method! (Consider making an override for your command)";
			e.printStackTrace();
		}
	}

	public boolean check(T handler, String[] params)
	{
		if (params.length >= this.minParams && ((this.maxParams >= 0) ? params.length <= this.maxParams : true))
			return true;
		if (params.length < this.minParams)
			missingParameters(handler, params);
		else
			if (params.length > this.maxParams)
				tooManyParameters(handler, params);
		return false;
	}

	public void tooManyParameters(T handler, String[] params)
	{
		this.message = "Too many parameters!";
	}

	public void missingParameters(T handler, String[] params)
	{
		this.message = "Missing parameters!";
	}

	public abstract void execute(T handler, String[] params);
}
