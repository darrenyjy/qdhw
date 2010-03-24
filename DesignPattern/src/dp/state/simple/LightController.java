package dp.state.simple;

public class LightController
{
	State state;
	State lightOnState;
	State lightOffState;

	public LightController()
	{
		super();
		this.lightOffState = new LightOffState(this);
		this.lightOnState = new LightOnState(this);
		this.state = lightOffState;
	}

	State getState()
	{
		return state;
	}

	State getLightOnState()
	{
		return lightOnState;
	}

	State getLightOffState()
	{
		return lightOffState;
	}

	public void turnOn()
	{
		state.turnOn();
	}

	public void turnOff()
	{
		state.turnOff();
	}

	void setState(State state)
	{
		this.state = state;
	}

	public boolean isOn()
	{
		// TODO Auto-generated method stub
		return state.isOn();
	}
}
