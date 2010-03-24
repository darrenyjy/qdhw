package ch6.simple;

public class SimpleController
{
	Command command;

	public void setCommand(Command command)
	{
		this.command = command;
	}

	public void pushBotton()
	{
		command.execute();
	}
}
