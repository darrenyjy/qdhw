package com.lblabs.maths;

class StartCalculatePrime
{
	public static void main(String[] args) 
	{
		CheckPoint checkPoint = new CheckPoint();
		Primes primes = new Primes(checkPoint);
		long[] finalPrime;
		finalPrime = primes.calculatePrimes();
		int currentPrimeCount = checkPoint.getCurrentPrimeCount();
		for (int i = 1; i <= currentPrimeCount; i ++)
		{
			System.out.println(finalPrime[i]);
		}
	}
}