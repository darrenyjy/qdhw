package com.lblabs.maths;

class PrimeCalculator extends Thread
{

	/*
	 * (non-Javadoc)
	 * 
	 * @see java.lang.Runnable#run()
	 */
	@Override
	public void run()
	{
		CheckPoint checkPoint = new CheckPoint();
		Primes primes = new Primes(checkPoint);
		long[] finalPrime;
		finalPrime = primes.calculatePrimes();
		int currentPrimeCount = checkPoint.getCurrentPrimeCount();
		for (int i = 1; i <= currentPrimeCount; i++)
		{
			System.out.println(finalPrime[i]);
		}
	}

	public static void main(String args[])
	{
		new PrimeCalculator().run();
	}
}