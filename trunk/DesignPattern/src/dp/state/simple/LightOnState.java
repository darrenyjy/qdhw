package dp.state.simple;

public class LightOnState implements State
{
	LightController lc;

	public LightOnState(LightController lc)
	{
		super();
		this.lc = lc;
	}

	@Override
	public boolean isOn()
	{
		// TODO Auto-generated method stub
		return true;
	}

	@Override
	public void turnOff()
	{
		// TODO Auto-generated method stub
		System.out.println("Turn light off.");
		lc.setState(lc.getLightOffState());

	}

	@Override
	public void turnOn()
	{
		// TODO Auto-generated method stub
		System.out.println("THE LIGHT IS ALREADY ON!");
	}

}
