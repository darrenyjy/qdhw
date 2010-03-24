package dp.command.simple;

public class LightImpl implements Light
{
	boolean on;

	/*
	 * (non-Javadoc)
	 * 
	 * @see dp.command.simple.ILight#turnOn()
	 */
	public void turnOn()
	{
		on = true;
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see dp.command.simple.ILight#turnOff()
	 */
	public void turnOff()
	{
		on = false;
	}

	@Override
	public boolean isOn()
	{
		// TODO Auto-generated method stub
		return this.on;
	}
}
