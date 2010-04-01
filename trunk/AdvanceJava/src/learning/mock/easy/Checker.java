package learning.mock.easy;

import java.util.Calendar;

public class Checker
{
	private Environmental env;

	public Checker(Environmental env)
	{
		this.env = env;
	}

	public void reminder()
	{
		Calendar cal = Calendar.getInstance();
		cal.setTimeInMillis(env.getTime());
		int hour = cal.get(Calendar.HOUR_OF_DAY);
		if (hour >= 17)
		{
			env.playWavFile("quit_whistle.wav");
		}
	}
}
