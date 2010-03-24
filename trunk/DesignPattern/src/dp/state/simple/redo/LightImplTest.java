package dp.state.simple.redo;

import static org.junit.Assert.assertFalse;
import static org.junit.Assert.assertTrue;

import org.junit.Before;
import org.junit.Test;

public class LightImplTest
{
	Light light;

	@Before
	public void setUp() throws Exception
	{
		light = new LightImpl();
	}

	@Test
	public void testIsOn()
	{
		assertFalse(light.isOn());
		light.turnOn();
		assertTrue(light.isOn());
	}

	@Test
	public void testTurnOff()
	{
		assertFalse(light.isOn());
		light.turnOff();
		assertFalse(light.isOn());
	}

	@Test
	public void testTurnOn()
	{
		assertFalse(light.isOn());
		light.turnOn();
		assertTrue(light.isOn());
	}

	@Test
	public void testTurnOffTurnOn()
	{
		assertFalse(light.isOn());
		light.turnOff();
		assertFalse(light.isOn());
		light.turnOn();
		assertTrue(light.isOn());
	}

	@Test
	public void testTurnOnTurnOff()
	{
		assertFalse(light.isOn());
		light.turnOn();
		assertTrue(light.isOn());
		light.turnOff();
		assertFalse(light.isOn());
	}
}
