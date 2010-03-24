package ch3;

public class Espresso implements Beverage
{
	public Espresso()
	{
		this(BeverageSize.GRANDE);
	}
	public Espresso(BeverageSize size)
	{
		
	}
	public double getCost()
	{
		// TODO Auto-generated method stub
		return 1.99;
	}

	public String getDescription()
	{
		// TODO Auto-generated method stub
		return "Espresso";
	}

	public BeverageSize getSize()
	{
		// TODO Auto-generated method stub
		return null;
	}

}
