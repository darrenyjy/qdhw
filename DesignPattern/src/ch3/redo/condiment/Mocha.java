package ch3.redo.condiment;

import ch3.redo.IBeverage;

public class Mocha extends CondimentDecorator
{

	public Mocha(IBeverage beverage)
	{
		super(beverage);
		this.price = 0.25;
		this.type = ", Mocha";
		// TODO Auto-generated constructor stub
	}

}
