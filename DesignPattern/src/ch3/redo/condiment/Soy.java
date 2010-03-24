package ch3.redo.condiment;

import ch3.redo.IBeverage;

public class Soy extends CondimentDecorator
{

	public Soy(IBeverage beverage)
	{
		super(beverage);
		this.price = 0.15;
		this.type = ", Soy";
		// TODO Auto-generated constructor stub
	}

}
