package com.lblabs.sync;

import java.util.Hashtable;

import com.lblabs.tools.Queue;
//import com.lblabs.business.BookRetailer;
import com.lblabs.business.Bank;

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
			}
			catch (Exception e)
			{
				System.out.println(e);
			}
			if (!resourceWaiterQueue.isEmpty())
			{
				potentialResourceHolder = (String)resourceWaiterQueue.peek();
				if (threadID.equals(potentialResourceHolder) && resourceAvailable)
				{
					resourceWaiterQueue.dequeue();
					break;
				}
			}
		}
	}

	/* for BookRetailer
	public Hashtable execute(BookRetailer bookRetailer, String parameter)
	{
		while (true)
		{
			try
			{
//				Thread.sleep(3000);
				Thread.sleep(30);
			}
			catch (Exception e)
			{
				System.out.println(e);
			}
			if (resourceAvailable)
			{
//System.out.println("\nBefore Buy ...");
				resourceAvailable = false;
				Hashtable responseHash = new Hashtable();
				responseHash = bookRetailer.buyBooks(parameter);
				resourceAvailable = true;
//System.out.println("After Buy ...\n");
				return responseHash;
			}
		}
	}
	*/

	public float deposit(Bank bank, String accountName, float accountBalance, float depositAmount)
	{
		while (true)
		{
			try
			{
				Thread.sleep(30);
			}
			catch (Exception e)
			{
				System.out.println(e);
			}
			if (resourceAvailable)
			{
				resourceAvailable = false;
				accountBalance = bank.deposit(accountName, accountBalance, depositAmount);
				resourceAvailable = true;
				return accountBalance;
			}
		}
	}

	public float withdraw(Bank bank, String accountName, float accountBalance, float depositAmount)
	{
		while (true)
		{
			try
			{
				Thread.sleep(30);
			}
			catch (Exception e)
			{
				System.out.println(e);
			}
			if (resourceAvailable)
			{
				resourceAvailable = false;
				accountBalance = bank.withdraw(accountName, accountBalance, depositAmount);
				resourceAvailable = true;
				return accountBalance;
			}
		}
	}
}
