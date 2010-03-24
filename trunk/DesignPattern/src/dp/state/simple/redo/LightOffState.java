package dp.state.simple.redo;

class LightOffState implements LightState
{
	Light light;

	public LightOffState(Light light)
	{
		this.light = light;
	}

	@Override
	public boolean isOn()
	{
		return false;
	}

	@Override
	public void turnOff()
	{
		System.out.println("The light is already OFF!");
	}

	@Override
	public void turnOn()
	{
		System.out.println("Now turn on the light.");
		light.setState(light.getLightOnState());
	}

}
