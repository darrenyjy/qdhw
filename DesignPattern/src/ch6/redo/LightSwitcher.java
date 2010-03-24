package ch6.redo;

public class LightSwitcher
{
	Command currentCommand;
	Command onCommand;
	Command offCommand;

	public LightSwitcher()
	{
		currentCommand = onCommand;
	}

	void setOnCommand(Command onCommand)
	{
		this.onCommand = onCommand;
	}

	void setOffCommand(Command offCommand)
	{
		this.offCommand = offCommand;
	}

	void setCommand(Command command)
	{
		this.currentCommand = command;
	}

	public void push()
	{
		currentCommand.execute();
		if (currentCommand == this.onCommand)
			currentCommand = this.offCommand;
		else
			currentCommand = this.onCommand;
	}
}
