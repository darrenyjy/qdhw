package ch1.duck;

public class DuckSimulator
{

	/**
	 * @param args
	 */
	public static void main(String[] args)
	{

		Duck md = new MallardDuck();
		Duck td = new ToyDuck();
		
		md.performFly();
		md.performQuack();
		md.performSwim();
		System.out.println();
		td.performFly();
		td.performQuack();
		td.performSwim();
		
		td.setQuackBehavior(new MuteQuack());
		td.performQuack();
		
	}

}
