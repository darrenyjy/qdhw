package ch3;

public class Whip extends CondimentDecorator
{
	static double price =0.45; 
	static String description = ", Whip";
	public Whip(Beverage beverage)
	{
		this.beverage = beverage;
	}
	public double getCost()
	{
		// TODO Auto-generated method stub
		return beverage.getCost() + price;
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
