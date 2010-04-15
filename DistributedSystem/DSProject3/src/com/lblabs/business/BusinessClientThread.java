package com.lblabs.business;

import java.util.Hashtable;

import com.lblabs.net.BookRetailer;

class BusinessClientThread extends Thread
{
	String threadID;
	String workSequence;
	BookRetailer bookRetailer = new BookRetailer();

	public BusinessClientThread(String threadID, String workSequence)
	{
		this.threadID = threadID;
		this.workSequence = workSequence;
	}

	public void run()
	{
		int index = workSequence.indexOf("#");
		String sequenceString;
		int sequence;
		while (index >= 0)
		{
			sequenceString = workSequence.substring(0, index);
			workSequence = workSequence.substring(index + 1);
			Integer bigInt = new Integer(sequenceString);
			sequence = bigInt.intValue();
			// System.out.println(threadID + ": sequence = " + sequence);

			switch (sequence)
			{
			case 0:
				System.out.println(threadID + ": isConnectionAvailable()");
				boolean isConnectionAvailable = false;
				while (!isConnectionAvailable)
				{
					isConnectionAvailable = bookRetailer
							.isConnectionAvailable(threadID);
					System.out.println(threadID + ": isConnectionAvailable = "
							+ isConnectionAvailable);
					try
					{
						Thread.sleep(2000);
					} catch (Exception e)
					{
						System.out.println(e);
					}
				}
				break;

			case 1:
				System.out.println(threadID + ": signIn()");
				boolean isSignedIn = bookRetailer.signIn(threadID);
				if (isSignedIn)
				{
					System.out.println(threadID + ": Signin succcessfully!");
				} else
				{
					System.out.println(threadID + ": Signin failed!");
				}
				break;

			case 2:
				System.out.println(threadID + ": signOut()");
				boolean isSignedOut = bookRetailer.signOut(threadID);
				if (isSignedOut)
				{
					System.out.println(threadID + ": Signout successfully!");
				} else
				{
					System.out.println(threadID + ": Signout failed!");
				}
				break;

			case 3:
				System.out.println(threadID + ": getPrice()");
				float price = bookRetailer.getPrice("C Language");
				System.out.println(threadID + ": price = " + price);
				break;

			case 4:
				System.out.println(threadID + ": getPublisher()");
				String publisher = bookRetailer.getPublisher("C Language");
				System.out.println(threadID + ": publisher = " + publisher);
				break;

			case 5:
				System.out.println(threadID + ": getAuthor()");
				String author = bookRetailer.getAuthor("C Language");
				System.out.println(threadID + ": author = " + author);
				break;

			case 6:
				System.out.println(threadID + ": buyBooks()");
				Hashtable responseHash = bookRetailer.buyBooks("libing",
						"111111", "1234567890", "C Language", 2);
				System.out.println(threadID + ": response = " + responseHash);
				break;
			}
			index = workSequence.indexOf("#");
		}
	}
}