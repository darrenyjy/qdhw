package dp.state.simple.redo;

class LightOnState implements LightState
{
	Light light;

	public LightOnState(Light light)
	{
		super();
		this.light = light;
	}

	@Override
	public void turnOff()
	{
		System.out.println("Now turn off the light.");
		light.setState(light.getLightOffState());
	}

	@Override
	public void turnOn()
	{
		System.out.println("The light is already ON!");
	}

	@Override
	public boolean isOn()
	{
		return true;
	}

}
