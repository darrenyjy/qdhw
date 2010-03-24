package ch3.redo.concretebeverage;

import ch3.redo.AbstractBeverage;
import ch3.redo.EBeverageSize;

public class HouseBlend extends AbstractBeverage
{

	public HouseBlend()
	{
		this(EBeverageSize.GRANDE);
	}

	public HouseBlend(EBeverageSize size)
	{
		super(size);
		this.type = "House Blend Coffee";
	}

	@Override
	protected double calculateCost()
	{
		double price = 0;
		if (size == EBeverageSize.TALL)
			price = 0.7;
		else if (EBeverageSize.GRANDE == size)
			price = 0.9;
		else if (EBeverageSize.VENTI == size)
			price = 1.2;
		return price;
	}

}
