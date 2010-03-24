package ch3;

public class DarkRoast implements Beverage
{
	BeverageSize size;
	double price;
	String description = "Dark Roast";
	
	public DarkRoast()
	{
		size = BeverageSize.GRANDE;
	}
	
	public DarkRoast(BeverageSize beverageSize)
	{
		this.size = beverageSize;
	}
	public double getCost()
	{
		// TODO Auto-generated method stub

		if(size == BeverageSize.TALL)
			price = 0.49;
		else if(BeverageSize.GRANDE == size)
			price = 0.59;
		else if(BeverageSize.VENTI == size)
			price = 0.69;
		return price;
	}

	public String getDescription()
	{
		// TODO Auto-generated method stub
		return size+" "+description;
	}

	public BeverageSize getSize()
	{
		// TODO Auto-generated method stub
		return size;
	}
	
	

}
