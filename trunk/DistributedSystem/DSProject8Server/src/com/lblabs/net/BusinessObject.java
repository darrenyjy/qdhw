package com.lblabs.net;

import java.util.Hashtable;

import com.lblabs.business.MovieRetailer;
import com.lblabs.sync.Locker;
import com.lblabs.sync.Mutexer;
import com.lblabs.tools.Tools;

class BusinessObject
{
	Tools tools = new Tools();
	MovieRetailer movieRetailer = new MovieRetailer();
	String threadID;
	Mutexer mutexer;
	Locker locker;

	public BusinessObject(String threadID, Mutexer mutexer, Locker locker)
	{
		this.threadID = threadID;
		this.mutexer = mutexer;
		this.locker = locker;
	}

	public Hashtable invoke(String methodName, String parameterString)
	{
		Hashtable parameterHash = tools.convertStringToHash(parameterString);
		// System.out.println("parameterHash (BusinessObject) = " +
		// parameterHash);
		Hashtable responseHash = new Hashtable();
		if (methodName.equals("signUp"))
		{
			if (locker.getReadPermission())
			{
				locker.setReadLock(threadID);
				String newAccountName = (String) parameterHash.get("0");
				String newAccountPassword = (String) parameterHash.get("1");
				String newCreditCardNumber = (String) parameterHash.get("2");
				responseHash.put("0", tools
						.convertBooleanToString(movieRetailer.signUp(
								newAccountName, newAccountPassword,
								newCreditCardNumber)));
				locker.releaseReadLock(threadID);
			} else
			{
				responseHash = null;
			}
		}
		if (methodName.equals("signIn"))
		{
			if (locker.getReadPermission())
			{
				locker.setReadLock(threadID);
				String inputAccountName = (String) parameterHash.get("0");
				String inputAccountPassword = (String) parameterHash.get("1");
				responseHash.put("0", tools
						.convertBooleanToString(movieRetailer.signIn(
								inputAccountName, inputAccountPassword)));
				System.out.println("responseHash (invoke) = " + responseHash);
				locker.releaseReadLock(threadID);
			} else
			{
				responseHash = null;
			}
		}
		if (methodName.equals("getPrice"))
		{
			if (locker.getReadPermission())
			{
				locker.setReadLock(threadID);
				String bookName = (String) parameterHash.get("0");
				responseHash.put("0", tools.convertFloatToString(movieRetailer
						.getPrice(bookName)));
				locker.releaseReadLock(threadID);
			} else
			{
				responseHash = null;
			}
		}
		if (methodName.equals("getPublisher"))
		{
			if (locker.getReadPermission())
			{
				locker.setReadLock(threadID);
				String bookName = (String) parameterHash.get("0");
				responseHash.put("0", movieRetailer.getPublisher(bookName));
				locker.releaseReadLock(threadID);
			} else
			{
				responseHash = null;
			}
		}
		if (methodName.equals("getAuthor"))
		{
			if (locker.getReadPermission())
			{
				locker.setReadLock(threadID);
				String bookName = (String) parameterHash.get("0");
				responseHash.put("0", movieRetailer.getAuthor(bookName));
				locker.releaseReadLock(threadID);
			} else
			{
				responseHash = null;
			}
		}
		if (methodName.equals("putIntoShoppingCart"))
		{
			if (locker.getReadPermission())
			{
				String bookName = (String) parameterHash.get("0");
				int quantity = tools.convertStringToInt((String) parameterHash
						.get("1"));
				responseHash.put("0", tools
						.convertBooleanToString(movieRetailer
								.putIntoShoppingCart(bookName, quantity)));
			} else
			{
				responseHash = null;
			}
		}
		if (methodName.equals("moveOutShoppingCart"))
		{
			String bookName = (String) parameterHash.get("0");
			int quantity = tools.convertStringToInt((String) parameterHash
					.get("1"));
			responseHash.put("0", tools.convertBooleanToString(movieRetailer
					.moveOutShoppingCart(bookName, quantity)));
		}
		if (methodName.equals("getShoppingCart"))
		{
			responseHash = movieRetailer.getShoppingCart();
			// System.out.println("responseHash (BusinessObject) = " +
			// responseHash);
		}
		if (methodName.equals("buyMovies"))
		{
			if (locker.getWritePermission())
			{
				locker.setWriteLock(threadID);
				String inputCreditCardNumber = (String) parameterHash.get("0");
				this.mutexer.doMutex(threadID);
				responseHash = this.mutexer.execute(movieRetailer,
						inputCreditCardNumber);
				locker.releaseWriteLock(threadID);
			} else
			{
				responseHash = null;
			}
		}
		return responseHash;
	}
}
