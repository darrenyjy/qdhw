package ch10;

public class GameMachine
{
	private State noQuarterStaer;
	private State playState;
	private State gameOverState;
	private State state;

	State getState()
	{
		return state;
	}

	void setState(State state)
	{
		this.state = state;
	}

	int count;

	State getNoQuarterStaer()
	{
		return noQuarterStaer;
	}

	State getPlayState()
	{
		return playState;
	}

	State getGameOverState()
	{
		return gameOverState;
	}

	int getCount()
	{
		return count;
	}

}
