package dp.command.simple;

public class LightTester
{

	/**
	 * @param args
	 */
	public static void main(String[] args)
	{
		// TODO Auto-generated method stub
		Light light = new LightImpl();
		LightSwitcher switcher = new LightSwitcher();

		Command lightOnCommand = new LightOnCommand(light);
		Command lightOffCommand = new LightOffCommand(light);

		switcher.setCommand(lightOnCommand);
		switcher.pushButton();

		switcher.setCommand(lightOffCommand);
		switcher.pushButton();
	}

}
