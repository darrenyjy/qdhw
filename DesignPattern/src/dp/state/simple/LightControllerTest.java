package dp.state.simple;

import static org.junit.Assert.assertFalse;
import static org.junit.Assert.assertTrue;

import org.junit.Before;
import org.junit.Test;

public class LightControllerTest
{
	LightController lc;
	State state;

	@Before
	public void setUp() throws Exception
	{
		lc = new LightController();

	}

	@Test
	public void testGetState()
	{
		State state = lc.getState();
		assertTrue(state instanceof LightOffState);
	}

	@Test
	public void testTurnOn()
	{
		assertFalse(lc.isOn());
		lc.turnOn();
		assertTrue(lc.isOn());
		lc.turnOn();
		assertTrue(lc.isOn());
	}

	@Test
	public void testTurnOff()
	{
		assertFalse(lc.isOn());
		lc.turnOff();
		assertFalse(lc.isOn());
		lc.turnOff();
		assertFalse(lc.isOn());
	}

	@Test
	public void testTurnOnTurnOff()
	{
		assertFalse(lc.isOn());
		lc.turnOn();
		assertTrue(lc.isOn());
		lc.turnOff();
		assertFalse(lc.isOn());
	}

	@Test
	public void testTurnOffTurnOn()
	{
		assertFalse(lc.isOn());
		lc.turnOff();
		assertFalse(lc.isOn());
		lc.turnOn();
		assertTrue(lc.isOn());
	}
}
