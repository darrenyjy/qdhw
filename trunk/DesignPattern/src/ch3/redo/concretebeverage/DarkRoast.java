package ch3.redo.concretebeverage;

import ch3.redo.AbstractBeverage;
import ch3.redo.EBeverageSize;

public class DarkRoast extends AbstractBeverage
{
	public DarkRoast()
	{
		this(EBeverageSize.GRANDE);
	}

	public DarkRoast(EBeverageSize size)
	{
		super(size);
		this.type = "Dark Roast";
	}

	@Override
	protected double calculateCost()
	{
		double price = 0;
		if (size == EBeverageSize.TALL)
			price = 0.4;
		else if (EBeverageSize.GRANDE == size)
			price = 0.5;
		else if (EBeverageSize.VENTI == size)
			price = 0.6;
		return price;
	}

}
