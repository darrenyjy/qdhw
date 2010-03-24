package ch3.redo.condiment;

import ch3.redo.IBeverage;

public class Whip extends CondimentDecorator
{
	public Whip(IBeverage beverage)
	{
		super(beverage);
		this.price = 0.10;
		this.type = ", Whip";
	}
}
