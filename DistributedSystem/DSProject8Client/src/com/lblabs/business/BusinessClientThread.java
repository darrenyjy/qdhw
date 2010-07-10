package com.lblabs.business;

import java.util.Hashtable;

import com.lblabs.net.BookRetailer;

class BusinessClientThread extends Thread
{
	String threadID;
	BookRetailer bookRetailer = new BookRetailer();
	float price;
	String publisher;
	String author;

	public BusinessClientThread(String threadID)
	{
		this.threadID = threadID;
	}

	public void run()
	{
		System.out.println(threadID + ": requesting online");
		if (bookRetailer.isConnectionAvailable(threadID))
		{
			System.out.println(threadID + ": is online");
			if (bookRetailer.signInServer(threadID))
			{
				if (bookRetailer.signIn("libing", "111111"))
				{
					price = bookRetailer.getPrice("C Language");
					System.out.println(threadID + ": price = " + price);
					if (price <= 50)
					{
						publisher = bookRetailer.getPublisher("C Language");
						System.out.println(threadID + ": publisher = "
								+ publisher);
						if (publisher.equals("Princeton"))
						{
							author = bookRetailer.getAuthor("C Language");
							System.out.println(threadID + ": author = "
									+ author);
							if (author.equals("Mike"))
							{
								bookRetailer.putIntoShoppingCart("C Language",
										5);
								System.out.println(threadID + ": "
										+ bookRetailer.getShoppingCart());
								bookRetailer.putIntoShoppingCart("Java Basics",
										3);
								System.out.println(threadID + ": "
										+ bookRetailer.getShoppingCart());
								bookRetailer.moveOutShoppingCart("C Language",
										3);
								System.out.println(threadID + ": "
										+ bookRetailer.getShoppingCart());

								Hashtable responseHash = bookRetailer
										.buyBooks("1234567890");
								System.out.println(threadID
										+ ": responseHash = " + responseHash);
							}
						} else
						{
							System.out
									.println("The publisher is not my favorite!");
						}
					} else
					{
						System.out.println("The price is too high!");
					}
					bookRetailer.signOutServer(threadID);
					System.out.println(threadID + ": Signed out");
				}
			}
		}
	}
}