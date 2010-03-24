package dp.command.undo;

public class LightOffCommand implements Command
{
	private Light light;
	private int level;

	public LightOffCommand(Light light)
	{
		super();
		this.light = light;
	}

	@Override
	public void execute()
	{
		level = light.getLevel();
		light.turnOff();
	}

	@Override
	public void undo()
	{
		light.dim(level);
	}

}
