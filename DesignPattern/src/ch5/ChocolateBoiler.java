package ch5;

public class ChocolateBoiler
{
	private boolean empty = true;
	private boolean boiled = false;
	private volatile static ChocolateBoiler instance= null;
	
	private ChocolateBoiler()
	{		
	}

	public ChocolateBoiler getInstance()
	{
		if(instance == null)
		{
			synchronized(ChocolateBoiler.class)
			{
				if(instance == null)
				{
					instance = new ChocolateBoiler();
				}
			}
			
		}
		return instance;
	}
}
