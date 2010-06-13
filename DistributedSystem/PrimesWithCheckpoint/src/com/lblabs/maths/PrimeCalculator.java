package com.lblabs.maths;

class PrimeCalculator
{

	/*
	 * (non-Javadoc)
	 * 
	 * @see java.lang.Runnable#run()
	 */
	public void run()
	{
		CheckPoint checkPoint = new CheckPoint();
		Primes primes = new Primes(checkPoint);
		for (int i = 0; i < 3; i++)
		{
			primes.calculatePrimes();
			checkPoint.increaseStopBy(10);
		}

	}

	public static void main(String args[])
	{
		new PrimeCalculator().run();
	}
}