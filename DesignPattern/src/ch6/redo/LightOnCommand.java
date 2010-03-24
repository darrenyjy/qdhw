/**
 * 
 */
package ch6.redo;

/**
 * @author Coddy Created at 2010-1-21 下午02:47:56
 */
public class LightOnCommand implements Command
{

	private Light light;

	/**
	 * 
	 */
	public LightOnCommand(Light light2)
	{
		light = light2;
		// TODO Auto-generated constructor stub
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see ch6.redo.Command#execute()
	 */
	@Override
	public void execute()
	{
		light.turnOn();
	}

}
