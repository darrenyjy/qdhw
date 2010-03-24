package ch3.redo.condiment;

import ch3.redo.EBeverageSize;
import ch3.redo.IBeverage;

public abstract class CondimentDecorator implements IBeverage
{
	protected IBeverage beverage;
	protected double price = 0;
	protected String type = "";

	public CondimentDecorator(IBeverage beverage)
	{
		this.beverage = beverage;
	}

	public EBeverageSize getSize()
	{
		// TODO Auto-generated method stub
		return beverage.getSize();
	}
	
	public double getCost()
	{
		return beverage.getCost() + price;
	}

	public String getDescription()
	{
		return beverage.getDescription() + type;
	}

}
