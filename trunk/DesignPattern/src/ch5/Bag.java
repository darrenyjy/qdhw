package ch5;

public class Bag
{
	private volatile static Bag instance = null;
	private boolean taken = false;

	public static Bag getInstance()
	{
		if(instance ==null)
		{
			synchronized(Bag.class)
			{
				if(instance == null)
				{
					instance = new Bag();
				}
			}
		}
		return instance;
	}
	
	public void takeBag()
	{
		if(this.isTaken())
			System.out.println("The bag is taken");
		else
		{
			System.out.println("Take the bag");
			this.setTaken(true);
		}
	}

	public void returnBag()
	{
		
	}
	public boolean isTaken()
	{
		return taken;
	}

	public void setTaken(boolean taken)
	{
		this.taken = taken;
	}
}
