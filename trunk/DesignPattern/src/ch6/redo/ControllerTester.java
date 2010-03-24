package ch6.redo;

public class ControllerTester
{

	/**
	 * @param args
	 */
	public static void main(String[] args)
	{
		// TODO Auto-generated method stub
		LightSwitcher lc = new LightSwitcher();
		Light l = new Light();
		LightOnCommand on = new LightOnCommand(l);
		LightOffCommand off = new LightOffCommand(l);

		System.out.println(l.isOn());
		lc.setCommand(on);
		lc.push();

		System.out.println(l.isOn());
		lc.setCommand(off);
		lc.push();
		System.out.println(l.isOn());

	}

}
