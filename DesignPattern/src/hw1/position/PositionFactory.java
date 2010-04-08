package hw1.position;

public class PositionFactory
{
	private static PositionFactory instance;

	private PositionFactory()
	{

	}

	public static PositionFactory getInstance()
	{
		if (instance == null)
		{
			instance = new PositionFactory();
		}
		return instance;
	}

	public Position createManagerPosition()
	{
		return new ManagerPosition();
	}

	public Position createEmployeePosition()
	{
		return new EmployeePosition();
	}
}
