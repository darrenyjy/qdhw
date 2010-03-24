package dp.state.simple.redo;

public class LightImpl implements Light
{
	private LightState state;
	private LightState lightOnState;
	private LightState lightOffState;

	public LightImpl()
	{
		super();
		lightOnState = new LightOnState(this);
		lightOffState = new LightOffState(this);
		state = lightOffState;

	}

	public LightState getLightOnState()
	{
		return lightOnState;
	}

	public LightState getLightOffState()
	{
		return lightOffState;
	}

	@Override
	public boolean isOn()
	{
		return state.isOn();
	}

	@Override
	public void turnOff()
	{
		state.turnOff();
	}

	@Override
	public void turnOn()
	{
		state.turnOn();
	}

	@Override
	public void setState(LightState state)
	{
		this.state = state;
	}

}
