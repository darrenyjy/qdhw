package ch6.redo;

import static org.junit.Assert.assertFalse;
import static org.junit.Assert.assertTrue;

import org.junit.Before;
import org.junit.Test;

public class LightSwitcherTest
{
	LightSwitcher ls;
	Light light;

	@Before
	public void setUp() throws Exception
	{
		ls = new LightSwitcher();
		light = new Light();
		Command onCommand = new LightOnCommand(light);
		Command offCommand = new LightOffCommand(light);
		ls.setOnCommand(onCommand);
		ls.setOffCommand(offCommand);
		ls.setCommand(onCommand);
	}

	@Test
	public void testPush()
	{
		ls.push();
		assertTrue(light.isOn());

		ls.push();
		assertFalse(light.isOn());

		ls.push();
		assertTrue(light.isOn());
	}

}
