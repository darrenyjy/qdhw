package ch3;

public class Mocha extends CondimentDecorator
{
	static double price;
	static String description;
	
	public Mocha(Beverage beverage)
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
		return beverage.getDescription()+ description;
	}
	public BeverageSize getSize()
	{
		// TODO Auto-generated method stub
		return beverage.getSize();
	}

}
