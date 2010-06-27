package com.lblabs.business;

import com.lblabs.net.Bank;

class TransactionU extends Thread
{
	String threadID;
	/*
	 * BookRetailer bookRetailer = new BookRetailer(); float price; String
	 * publisher; String author;
	 */
	Bank bank = new Bank();

	public TransactionU(String threadID)
	{
		this.threadID = threadID;
	}

	public void run()
	{
		if (bank.isConnectionAvailable(threadID))
		{
			System.out.println(threadID + ": is online");
			if (bank.signInServer(threadID))
			{
				float accountBBalance = bank.getAccountBalance("B");
				try
				{
					Thread.sleep(3000);
				} catch (Exception e)
				{
					System.out.println(e);
				}
				float accountCBalance = bank.getAccountBalance("C");
				accountCBalance = bank.withdraw("C", accountCBalance, 50);
				accountBBalance = bank.deposit("B", accountBBalance, 50);

				System.out.println(threadID + ": Balance of C: "
						+ bank.getAccountBalance("C"));
				System.out.println(threadID + ": Balance of B: "
						+ bank.getAccountBalance("B"));
				System.out.println(threadID + ": TotalDeposit: "
						+ bank.getTotalDeposit());
				bank.signOutServer(threadID);
				System.out.println(threadID + ": Signed out");
			}
		}
	}
}
