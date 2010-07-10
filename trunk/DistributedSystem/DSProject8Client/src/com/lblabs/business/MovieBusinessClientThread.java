/**
 * 
 */
package com.lblabs.business;

/**
 * @author Coddy created on 2010-7-10
 *
 */
import java.util.Hashtable;

import com.lblabs.net.MovieRetailer;

class MovieBusinessClientThread extends Thread
{
	String threadID;
	MovieRetailer movieRetailer = new MovieRetailer();
	float price;
	String publisher;
	String author;

	public MovieBusinessClientThread(String threadID)
	{
		this.threadID = threadID;
	}

	public void run()
	{
		System.out.println(threadID + ": requesting online");
		if (movieRetailer.isConnectionAvailable(threadID))
		{
			System.out.println(threadID + ": is online");
			if (movieRetailer.signInServer(threadID))
			{
				if (movieRetailer.signIn("libing", "111111"))
				{
					price = movieRetailer.getPrice("C Language");
					System.out.println(threadID + ": price = " + price);
					if (price <= 50)
					{
						publisher = movieRetailer.getPublisher("C Language");
						System.out.println(threadID + ": publisher = "
								+ publisher);
						if (publisher.equals("Princeton"))
						{
							author = movieRetailer.getAuthor("C Language");
							System.out.println(threadID + ": author = "
									+ author);
							if (author.equals("Mike"))
							{
								movieRetailer.putIntoShoppingCart("C Language",
										5);
								System.out.println(threadID + ": "
										+ movieRetailer.getShoppingCart());
								movieRetailer.putIntoShoppingCart(
										"Java Basics", 3);
								System.out.println(threadID + ": "
										+ movieRetailer.getShoppingCart());
								movieRetailer.moveOutShoppingCart("C Language",
										3);
								System.out.println(threadID + ": "
										+ movieRetailer.getShoppingCart());

								Hashtable responseHash = movieRetailer
										.buyMovies("1234567890");
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
					movieRetailer.signOutServer(threadID);
					System.out.println(threadID + ": Signed out");
				}
			}
		}
	}
}