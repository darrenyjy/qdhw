package com.lblabs.business;

import java.util.Hashtable;
import java.util.Enumeration;

//import com.lblabs.net.BookRetailer;
import com.lblabs.net.Bank;

class TransactionT extends Thread
{
	String threadID;
	/*
	BookRetailer bookRetailer = new BookRetailer();
	float price;
	String publisher;
	String author;
	*/
	Bank bank = new Bank();

	public TransactionT(String threadID)
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
				float accountABalance = bank.getAccountBalance("A");
				accountABalance = bank.withdraw("A", accountABalance, 50);
				accountBBalance = bank.deposit("B", accountBBalance, 50);

				System.out.println(threadID + ": Balance of A: " + bank.getAccountBalance("A"));
				System.out.println(threadID + ": Balance of B: " + bank.getAccountBalance("B"));
				System.out.println(threadID + ": TotalDeposit: " + bank.getTotalDeposit());
				bank.signOutServer(threadID);
System.out.println(threadID + ": Signed out");
			}
		}
	}
}