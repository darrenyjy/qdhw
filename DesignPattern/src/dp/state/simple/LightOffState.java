package dp.state.simple;

public class LightOffState implements State
{
	LightController lc;

	public LightOffState(LightController lc)
	{
		super();
		this.lc = lc;
	}

	@Override
	public boolean isOn()
	{
		// TODO Auto-generated method stub
		return false;
	}

	@Override
	public void turnOff()
	{
		// TODO Auto-generated method stub
		System.out.println("The light is already OFF!");

	}

	@Override
	public void turnOn()
	{
		// TODO Auto-generated method stub
		System.out.println("Turn on light.");
		lc.setState(lc.getLightOnState());
	}

}
