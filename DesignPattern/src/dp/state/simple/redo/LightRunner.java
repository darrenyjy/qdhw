package dp.state.simple.redo;

public class LightRunner
{

	/**
	 * @param args
	 */
	public static void main(String[] args)
	{
		// TODO Auto-generated method stub
		Light light = new LightImpl();
		LightSwitcher switcher = new LightSwitcher(light);
		System.out.println(light.isOn());
		for (int i = 0; i < 5; i++)
		{
			switcher.pushButton();
		}
		System.out.println(light.isOn());
	}

}
