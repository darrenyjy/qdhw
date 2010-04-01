package learning.mock.easy;

public class MockSystemEnvironment implements Environmental
{
	private long time = 0;
	private boolean playedWav = false;

	@Override
	public long getTime()
	{
		return time;
	}

	public void setTime(long time)
	{
		this.time = time;
	}

	@Override
	public void playWavFile(String fileName)
	{
		playedWav = true;
	}

	@Override
	public void resetWav()
	{
		playedWav = false;
	}

	@Override
	public boolean wavWasPlayed()
	{
		return playedWav;
	}

}
