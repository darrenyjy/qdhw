package dp.adapter.duck;

import java.util.Random;

public class DuckAdapter implements Turkey
{
	Duck duck;
	Random random;

	public DuckAdapter(Duck duck)
	{
		super();
		this.duck = duck;
		random = new Random();
	}

	@Override
	public void fly()
	{
		if (random.nextInt(4) == 0)
		{
			duck.fly();
		}
	}

	@Override
	public void gobble()
	{
		duck.quack();
	}

}
