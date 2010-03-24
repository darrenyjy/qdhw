package dp.state.simple.redo;

public interface Light
{
	public void turnOn();

	public void turnOff();

	public boolean isOn();

	public void setState(LightState state);

	LightState getLightOnState();

	LightState getLightOffState();
}
