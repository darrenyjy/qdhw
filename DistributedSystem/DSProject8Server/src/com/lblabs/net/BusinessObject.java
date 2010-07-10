package com.lblabs.net;

import java.util.Hashtable;

import com.lblabs.tools.Tools;
import com.lblabs.business.BookRetailer;
import com.lblabs.sync.Mutexer;
import com.lblabs.sync.Locker;

class BusinessObject
{
	Tools tools = new Tools();
	BookRetailer bookRetailer = new BookRetailer();
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
//System.out.println("parameterHash (BusinessObject) = " + parameterHash);
		Hashtable responseHash = new Hashtable();
		if (methodName.equals("signUp"))
		{
			if (locker.getReadPermission())
			{
				locker.setReadLock(threadID);
				String newAccountName = (String)parameterHash.get("0");
				String newAccountPassword = (String)parameterHash.get("1");
				String newCreditCardNumber = (String)parameterHash.get("2");
				responseHash.put("0", tools.convertBooleanToString(bookRetailer.signUp(newAccountName, newAccountPassword, newCreditCardNumber)));
				locker.releaseReadLock(threadID);
			}
			else
			{
				responseHash = null;
			}
		}
		if (methodName.equals("signIn"))
		{
			if (locker.getReadPermission())
			{
				locker.setReadLock(threadID);
				String inputAccountName = (String)parameterHash.get("0");
				String inputAccountPassword = (String)parameterHash.get("1");
				responseHash.put("0", tools.convertBooleanToString(bookRetailer.signIn(inputAccountName, inputAccountPassword)));
System.out.println("responseHash (invoke) = " + responseHash);
				locker.releaseReadLock(threadID);
			}
			else
			{
				responseHash = null;
			}
		}
		if (methodName.equals("getPrice"))
		{
			if (locker.getReadPermission())
			{
				locker.setReadLock(threadID);
				String bookName = (String)parameterHash.get("0");
				responseHash.put("0", tools.convertFloatToString(bookRetailer.getPrice(bookName)));
				locker.releaseReadLock(threadID);
			}
			else
			{
				responseHash = null;
			}
		}
		if (methodName.equals("getPublisher"))
		{
			if (locker.getReadPermission())
			{
				locker.setReadLock(threadID);
				String bookName = (String)parameterHash.get("0");
				responseHash.put("0", bookRetailer.getPublisher(bookName));
				locker.releaseReadLock(threadID);
			}
			else
			{
				responseHash = null;
			}
		}
		if (methodName.equals("getAuthor"))
		{
			if (locker.getReadPermission())
			{
				locker.setReadLock(threadID);
				String bookName = (String)parameterHash.get("0");
				responseHash.put("0", bookRetailer.getAuthor(bookName));
				locker.releaseReadLock(threadID);
			}
			else
			{
				responseHash = null;
			}
		}
		if (methodName.equals("putIntoShoppingCart"))
		{
			if (locker.getReadPermission())
			{
				String bookName = (String)parameterHash.get("0");
				int quantity = tools.convertStringToInt((String)parameterHash.get("1"));
				responseHash.put("0", tools.convertBooleanToString(bookRetailer.putIntoShoppingCart(bookName, quantity)));
			}
			else
			{
				responseHash = null;
			}
		}
		if (methodName.equals("moveOutShoppingCart"))
		{
			String bookName = (String)parameterHash.get("0");
			int quantity = tools.convertStringToInt((String)parameterHash.get("1"));
			responseHash.put("0", tools.convertBooleanToString(bookRetailer.moveOutShoppingCart(bookName, quantity)));
		}
		if (methodName.equals("getShoppingCart"))
		{
			responseHash = bookRetailer.getShoppingCart();
//System.out.println("responseHash (BusinessObject) = " + responseHash);
		}
		if (methodName.equals("buyBooks"))
		{
			if (locker.getWritePermission())
			{
				locker.setWriteLock(threadID);
				String inputCreditCardNumber = (String)parameterHash.get("0");
				this.mutexer.doMutex(threadID);
				responseHash = this.mutexer.execute(bookRetailer, inputCreditCardNumber);
				locker.releaseWriteLock(threadID);
			}
			else
			{
				responseHash = null;
			}
		}
		return responseHash;
	}
}
