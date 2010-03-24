package dp.command.undo;

public class Light
{
	String location;
	int level;

	public Light(String location)
	{
		this.location = location;
	}

	public void turnOn()
	{
		level = 100;
		System.out.println("Light is on");
	}

	public void turnOff()
	{
		level = 0;
		System.out.println("Light is off");
	}

	public void dim(int level)
	{
		this.level = level;
		if (level == 0)
		{
			turnOff();
		} else
		{
			System.out.println("Light is dimmed to " + level + "%");
		}
	}

	public int getLevel()
	{
		return level;
	}
}
