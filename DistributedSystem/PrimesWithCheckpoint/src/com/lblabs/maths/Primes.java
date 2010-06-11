package com.lblabs.maths;

/* Compute prime numbers, after Knuth, Vol 1, Sec 1.3.2, Alg. "P".
 * Unlike Knuth, I don't build table formatting into
 * computational programs; output is one per line.
 * <p>
 * Note that there may be more efficient algorithms for finding primes.
 * Consult a good book on numerical algorithms.
 * @author Ian Darwin
 */
public class Primes
{
	// public static final long DEFAULT_STOP = 4294967295L;
	CheckPoint checkPoint;
	long DEFAULT_STOP; // 4294967295L
	int FP;
	int MAX;
	int currentPrimeCount;
	long[] currentPrimeArray;
	long[] prime;

	public Primes(CheckPoint checkPoint)
	{
		this.checkPoint = checkPoint;
		this.DEFAULT_STOP = checkPoint.getDEFAULT_STOP();
		System.out.println("DEFAULT_STOP = " + DEFAULT_STOP);
		this.FP = checkPoint.getFirstPrime();
		System.out.println("FP = " + FP);
		this.currentPrimeCount = checkPoint.getCurrentPrimeCount();
		System.out.println("currentPrimeCount = " + currentPrimeCount);
		this.MAX = checkPoint.getMAX() + currentPrimeCount;
		System.out.println("MAX = " + MAX);
		this.currentPrimeArray = checkPoint.getCurrentPrimes();

		if (this.currentPrimeCount > 1)
		{
			this.prime = new long[this.currentPrimeCount + this.MAX];
			System.arraycopy(this.currentPrimeArray, 0, prime, 1,
					this.currentPrimeCount);
		} else
		{
			this.prime = new long[this.MAX];
			this.prime[1] = this.FP;
		}
	}

	public long[] calculatePrimes()
	{
		// long[] prime = new long[MAX];

		long stop = this.DEFAULT_STOP;

		// prime[1] = this.FP; // P1 (ignore prime[0])

		long n;
		boolean isPrime;
		int j = this.currentPrimeCount; // numberFound
		if (this.currentPrimeCount == 1)
		{
			n = this.FP + 1; // odd candidates
			isPrime = true; // for 3
		} else
		{
			n = prime[j] + 2;
			isPrime = false;
		}
		System.out.println("j = " + j);
		System.out.println("n = " + n);
		do
		{
			if (isPrime)
			{
				if (j == MAX - 1)
				{
					// Grow array dynamically if needed
					long[] np = new long[MAX * 2];
					System.arraycopy(prime, 0, np, 0, MAX);
					MAX *= 2;
					prime = np;
				}
				prime[++j] = n; // P2
				System.out.println(n);
				System.out.println("j *** = " + j);
				checkPoint.createCheckPoint(n);
				isPrime = false;
			}
			n += 2; // P4

			for (int k = 2; k <= j && k < MAX; k++)
			{ // P5, P6, P8
				long q = n / prime[k];
				long r = n % prime[k];
				if (r == 0)
				{
					break;
				}
				if (q <= prime[k])
				{ // P7
					isPrime = true;
					break;
				}
			}
		} while (n < stop); // P3

		return prime;
	}
}