package ch6.simple;

public class SimpleControllerTest
{
	public static void main(String[] args)
	{
		SimpleController sc = new SimpleController();
		Light light = new Light();
		
		LightOnCommand lightOnCommand = new LightOnCommand(light);
		LightOffCommand lightOffCommand = new LightOffCommand(light);
		
		sc.setCommand(lightOnCommand);
		sc.pushBotton();
		
		sc.setCommand(lightOffCommand);
		sc.pushBotton();
		
	}
}
