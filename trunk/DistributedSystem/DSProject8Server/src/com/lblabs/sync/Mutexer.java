package com.lblabs.sync;

import java.util.Hashtable;

import com.lblabs.business.MovieRetailer;
import com.lblabs.tools.Queue;

public class Mutexer
{
	boolean resourceAvailable;
	Queue resourceWaiterQueue = new Queue();

	public Mutexer()
	{
		resourceAvailable = true;
	}

	public void doMutex(String threadID)
	{
		String potentialResourceHolder;
		resourceWaiterQueue.enqueue(threadID);
		while (true)
		{
			System.out.println(threadID + ": Right here waiting!");
			try
			{
				Thread.sleep(10);
			} catch (Exception e)
			{
				System.out.println(e);
			}
			if (!resourceWaiterQueue.isEmpty())
			{
				potentialResourceHolder = (String) resourceWaiterQueue.peek();
				if (threadID.equals(potentialResourceHolder)
						&& resourceAvailable)
				{
					resourceWaiterQueue.dequeue();
					break;
				}
			}
		}
	}

	public Hashtable execute(MovieRetailer movieRetailer, String parameter)
	{
		while (true)
		{
			try
			{
				// Thread.sleep(3000);
				Thread.sleep(30);
			} catch (Exception e)
			{
				System.out.println(e);
			}
			if (resourceAvailable)
			{
				// System.out.println("\nBefore Buy ...");
				resourceAvailable = false;
				Hashtable responseHash = new Hashtable();
				responseHash = movieRetailer.buyMovies(parameter);
				resourceAvailable = true;
				// System.out.println("After Buy ...\n");
				return responseHash;
			}
		}
	}
}
