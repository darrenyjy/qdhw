package learning.mock.easy;

import java.util.Calendar;

import junit.framework.TestCase;

public class CheckerTest extends TestCase
{

	protected void setUp() throws Exception
	{
		super.setUp();
	}

	public void testReminder()
	{
		MockSystemEnvironment env = new MockSystemEnvironment();
		Calendar cal = Calendar.getInstance();
		cal.set(Calendar.YEAR, 2009);
		cal.set(Calendar.MONTH, 11);
		cal.set(Calendar.DAY_OF_MONTH, 7);
		cal.set(Calendar.HOUR_OF_DAY, 16);
		cal.set(Calendar.MINUTE, 55);

		long t1 = cal.getTimeInMillis();
		env.setTime(t1);

		Checker checker = new Checker(env);
		checker.reminder();
		assertFalse(env.wavWasPlayed());

		t1 += (5 * 60 * 1000);
		env.setTime(t1);
		checker.reminder();
		assertTrue(env.wavWasPlayed());

		env.resetWav();
		t1 += 2 * 60 * 60 * 1000;
		env.setTime(t1);
		checker.reminder();
		assertTrue(env.wavWasPlayed());
	}

}
