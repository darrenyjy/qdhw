package com.lblabs.business;

import java.util.Hashtable;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;

import com.lblabs.net.BookRetailer;

class BusinessClientThread extends Thread
{
	String threadID;
	String workSequence;
	BookRetailer bookRetailer = new BookRetailer();
	private static Log log = LogFactory.getLog(BusinessClientThread.class);

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
			// log.debug(threadID + ": sequence = " + sequence);

			switch (sequence)
			{
			case 0:
				log.debug(threadID + ": isConnectionAvailable()");
				boolean isConnectionAvailable = false;
				while (!isConnectionAvailable)
				{
					isConnectionAvailable = bookRetailer
							.isConnectionAvailable(threadID);
					log.debug(threadID + ": isConnectionAvailable = "
							+ isConnectionAvailable);
					try
					{
						Thread.sleep(2000);
					} catch (Exception e)
					{
						log.error(e);
					}
				}
				break;

			case 1:
				log.debug(threadID + ": signIn()");
				boolean isSignedIn = bookRetailer.signIn(threadID);
				if (isSignedIn)
				{
					log.debug(threadID + ": Signin succcessfully!");
				} else
				{
					log.debug(threadID + ": Signin failed!");
				}
				break;

			case 2:
				log.debug(threadID + ": signOut()");
				boolean isSignedOut = bookRetailer.signOut(threadID);
				if (isSignedOut)
				{
					log.debug(threadID + ": Signout successfully!");
				} else
				{
					log.debug(threadID + ": Signout failed!");
				}
				break;

			case 3:
				log.debug(threadID + ": getPrice()");
				float price = bookRetailer.getPrice("C Language");
				log.debug(threadID + ": price = " + price);
				break;

			case 4:
				log.debug(threadID + ": getPublisher()");
				String publisher = bookRetailer.getPublisher("C Language");
				log.debug(threadID + ": publisher = " + publisher);
				break;

			case 5:
				log.debug(threadID + ": getAuthor()");
				String author = bookRetailer.getAuthor("C Language");
				log.debug(threadID + ": author = " + author);
				break;

			case 6:
				log.debug(threadID + ": buyBooks()");
				Hashtable responseHash = bookRetailer.buyBooks("libing",
						"111111", "1234567890", "C Language", 2);
				log.debug(threadID + ": response = " + responseHash);
				break;
			}
			index = workSequence.indexOf("#");
		}
	}
}