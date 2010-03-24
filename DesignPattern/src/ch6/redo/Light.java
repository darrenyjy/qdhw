package ch6.redo;

public class Light
{
	private boolean on;

	public Light()
	{
		on = false;
	}

	public void turnOn()
	{
		this.setOn(true);
	}

	public void turnOff()
	{
		this.setOn(false);
	}

	boolean isOn()
	{
		return on;
	}

	void setOn(boolean on)
	{
		this.on = on;
	}
}
