package dp.state.simple;

public class LightTester
{

	/**
	 * @param args
	 */
	public static void main(String[] args)
	{
		// TODO Auto-generated method stub
		LightController lc = new LightController();
		lc.turnOff();
		lc.isOn();
		lc.turnOn();
		lc.isOn();
		lc.turnOff();
		lc.isOn();

	}

}
