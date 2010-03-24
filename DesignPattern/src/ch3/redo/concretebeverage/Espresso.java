package ch3.redo.concretebeverage;

import ch3.redo.AbstractBeverage;
import ch3.redo.EBeverageSize;

public class Espresso extends AbstractBeverage
{

	public Espresso()
	{
		// TODO Auto-generated constructor stub
		this(EBeverageSize.GRANDE);
	}

	public Espresso(EBeverageSize size)
	{
		super(size);
		this.type = "Espresso Cold Drink";
		// TODO Auto-generated constructor stub
	}

	@Override
	protected double calculateCost()
	{
		// TODO Auto-generated method stub
		double price = 0;
		if (size == EBeverageSize.TALL)
			price = 0.3;
		else if (EBeverageSize.GRANDE == size)
			price = 0.4;
		else if (EBeverageSize.VENTI == size)
			price = 0.5;
		return price;	}

}
