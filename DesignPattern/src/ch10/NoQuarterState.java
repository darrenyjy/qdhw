package ch10;

public class NoQuarterState implements State
{
	GameMachine machine;

	public NoQuarterState(GameMachine machine)
	{
		super();
		this.machine = machine;
	}

	@Override
	public void finish()
	{
		System.out.println("You need to insert a quarter first.");
	}

	@Override
	public void insertQuarter()
	{
		// TODO Auto-generated method stub
		System.out.println("You've insert a quarter.");
		machine.setState(machine.getPlayState());

	}

	@Override
	public void playBall()
	{
		// TODO Auto-generated method stub
		System.out.println("No ball to play.");

	}

}
