package learning.mock.easy;

public class SystemEnvironment implements Environmental
{
	boolean playedWav = false;

	public long getTime()
	{
		return System.currentTimeMillis();
	}

	public void playWavFile(String fileName)
	{
		playedWav = true;
	}

	public boolean wavWasPlayed()
	{
		return playedWav;
	}

	public void resetWav()
	{
		playedWav = false;
	}
}
