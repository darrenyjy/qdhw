package dp.command.undo;

public class LightOnCommand implements Command
{
	Light light;
	int level;

	public LightOnCommand(Light light)
	{
		super();
		this.light = light;
	}

	@Override
	public void execute()
	{
		level = light.getLevel();
		light.turnOn();
	}

	@Override
	public void undo()
	{
		light.dim(level);
	}

}
