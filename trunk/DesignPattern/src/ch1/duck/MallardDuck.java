package ch1.duck;

public class MallardDuck extends Duck
{
	MallardDuck()
	{
		this.setFlyBehavior(new FlyWithWings());
		this.setQuackBehavior(new Quack());
	}

}
