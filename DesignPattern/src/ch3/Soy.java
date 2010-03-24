package ch3;

public class Soy extends CondimentDecorator
{
	static double price;
	static String description=", Soy";
	
	public Soy(Beverage beverage)
	{
		this.beverage = beverage;
	}
	
	public double getCost()
	{
		// TODO Auto-generated method stub
		return beverage.getCost()+price;
	}

	public String getDescription()
	{
		// TODO Auto-generated method stub
		return beverage.getDescription()+description;
	}

	public BeverageSize getSize()
	{
		// TODO Auto-generated method stub
		return beverage.getSize();
	}

}
