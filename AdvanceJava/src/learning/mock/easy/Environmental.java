package learning.mock.easy;

public interface Environmental
{
	public long getTime();

	public void playWavFile(String fileName);

	public boolean wavWasPlayed();

	public void resetWav();
}