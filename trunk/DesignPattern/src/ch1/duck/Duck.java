package ch1.duck;

public abstract class Duck
{
	private FlyBehavior flyBehavior;
	private QuackBehavior quackBehavior;
	
	public void performSwim()
	{
		System.out.println("All ducks flow, man!");
	}
	
	public void performFly()
	{
		flyBehavior.fly();
	}
	
	public void performQuack()
	{
		quackBehavior.quack();
	}

	public void setFlyBehavior(FlyBehavior flyBehavior)
	{
		this.flyBehavior = flyBehavior;
	}

	public void setQuackBehavior(QuackBehavior quackBehavior)
	{
		this.quackBehavior = quackBehavior;
	}
}
