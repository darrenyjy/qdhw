package dp.state.simple.redo;

public class LightSwitcher
{
	Light light;
	Command lightOnCommand;
	Command lightOffCommand;
	Command command;

	public LightSwitcher(Light light)
	{
		super();
		this.light = light;
		lightOffCommand = new LightOffCommand(light);
		lightOnCommand = new LightOnCommand(light);
		command = lightOnCommand;
	}

	public void pushButton()
	{
		command.execute();
		if (command instanceof LightOnCommand)
		{
			command = this.lightOffCommand;
		} else
		{
			command = this.lightOnCommand;
		}

	}
}
