package ch1.duck;

public class ToyDuck extends Duck
{
	ToyDuck()
	{
		this.setFlyBehavior(new FlyNoWay());
		this.setQuackBehavior(new Squeak());
	}

}
