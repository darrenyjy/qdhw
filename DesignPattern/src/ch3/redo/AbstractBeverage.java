package ch3.redo;

public abstract class AbstractBeverage implements IBeverage
{
	protected double cost;
	protected String type ="Unknown Beverage";
	protected EBeverageSize size;

	public AbstractBeverage(EBeverageSize size)
	{
		this.size = size;
	}
	
	public AbstractBeverage()
	{
		this.size = EBeverageSize.GRANDE;
	}
	

	public double getCost()
	{
		cost = calculateCost();
		return cost;
	}

	protected abstract double calculateCost();

	public String getDescription()
	{
		return size+" "+type;
	}

	public EBeverageSize getSize()
	{
		return size;
	}

}
