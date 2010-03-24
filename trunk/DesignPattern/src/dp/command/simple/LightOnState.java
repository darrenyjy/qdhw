package dp.command.simple;

public class LightOnState implements Light
{
	Light light;

	public LightOnState(Light light)
	{
		super();
		this.light = light;
	}

	@Override
	public boolean isOn()
	{
		// TODO Auto-generated method stub
		return light.isOn();
	}

	@Override
	public void turnOff()
	{
		System.out.println("The light is turned off.");
		light.turnOff();
	}

	@Override
	public void turnOn()
	{
		// TODO Auto-generated method stub

	}

}
