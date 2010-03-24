package dp.command.simple;

public class LightSwitcher
{
	Command command;

	Command getCommand()
	{
		return command;
	}

	void setCommand(Command command)
	{
		this.command = command;
	}

	public void pushButton()
	{
		command.execute();
	}
}
