package com.lblabs.business;

import java.io.*;

import java.util.Hashtable;
import java.util.Enumeration;

import com.ibm.xml.parser.TXDocument;
import com.ibm.xml.parser.Parser;
import org.w3c.dom.Document;
import org.w3c.dom.Element;

import com.lblabs.xmltool.SelectorByKeyTag;
import com.lblabs.xmltool.SelectorByTagAndWhere;
import com.lblabs.xmltool.SelectorForHash;
import com.lblabs.xmltool.LBXMLOperator;
import com.lblabs.tools.Tools;

public class BookRetailer
{
	String bookName;
	float bookPrice;
	String publisher;
	String author;
	float payment;
	int quantity;
	int inStock;
	float cash;
	String accountName;
	String accountPassword;
	String creditCardNumber;

	Hashtable orderFormHash = new Hashtable();
	Hashtable receiptFormHash = new Hashtable();

	Hashtable shoppingCartHash = new Hashtable();

	BookRetailerDB bookRetailerDB;
	Tools tools = new Tools();

	public BookRetailer()
	{
		bookRetailerDB = new BookRetailerDB();
	}


	public boolean signUp(String newAccountName, String newAccountPassword, String newCreditCardNumber)
	{
		if (!bookRetailerDB.isAccountNameExisted(newAccountName))
		{
			if (bookRetailerDB.saveAccount(newAccountName, newAccountPassword, newCreditCardNumber))
			{
				accountName = newAccountName;
				accountPassword = newAccountPassword;
				creditCardNumber = newCreditCardNumber;
				return true;
			}
			else
			{
				return false;
			}
		}
		else
		{
			return true;
		}
	}

	public boolean signIn(String inputAccountName, String inputAccountPassword)
	{
		if (bookRetailerDB.isAccountMatched(inputAccountName, inputAccountPassword))
		{
			accountName = inputAccountName;
			accountPassword = inputAccountPassword;
			return true;
		}
		else
		{
			return false;
		}
	}

	public float getPrice(String bookName)
	{
		return bookRetailerDB.getPrice(bookName);
	}

	public String getPublisher(String bookName)
	{
		return bookRetailerDB.getPublisher(bookName);
	}

	public String getAuthor(String bookName)
	{
		return bookRetailerDB.getAuthor(bookName);
	}

	public boolean putIntoShoppingCart(String bookName, int quantity)
	{
		if (bookRetailerDB.isBookExisted(bookName))
		{
			if (!shoppingCartHash.containsKey(bookName))
			{
				shoppingCartHash.put(bookName, tools.convertIntToString(quantity));
			}
			else
			{
				int currentQuantity = tools.convertStringToInt((String)shoppingCartHash.get(bookName));
				currentQuantity += quantity;
				shoppingCartHash.put(bookName, tools.convertIntToString(currentQuantity));
			}
		}
		else
		{
			return false;
		}
		return true;
	}

	public boolean moveOutShoppingCart(String bookName, int quantity)
	{
		if (shoppingCartHash.containsKey(bookName))
		{
			int currentQuantity = tools.convertStringToInt((String)shoppingCartHash.get(bookName));
			if (currentQuantity > quantity)
			{
				currentQuantity = currentQuantity - quantity;
				shoppingCartHash.put(bookName, tools.convertIntToString(currentQuantity));
			}
			else
			{
				shoppingCartHash.remove(bookName);
			}
			return true;
		}
		else
		{
			return false;
		}
	}

	public Hashtable getShoppingCart()
	{
		return shoppingCartHash;
	}

	public Hashtable buyBooks(String inputCreditCardNumber)
	{
		String str = null;
		String soldBooks = "";
		payment = 0;
//System.out.println("shoppingCartHash = " + shoppingCartHash);
//		for (int i = 0; i < shoppingCartHash.size(); i ++)
		Enumeration shoppingCartEnum = shoppingCartHash.keys();

		String bookName;

		while (shoppingCartEnum.hasMoreElements())
		{
			bookName = (String)shoppingCartEnum.nextElement();
			quantity = tools.convertStringToInt((String)shoppingCartHash.get(bookName));
//			selectedBook = (SelectedBook)shoppingCartHash.get(bookName);
//System.out.println("selectedBook.bookName = " + selectedBook.bookName);
//System.out.println("selectedBook.quantity = " + selectedBook.quantity);
			inStock = bookRetailerDB.getInstock(bookName);
			if (inStock >= quantity)
			{
				bookPrice = bookRetailerDB.getPrice(bookName);
				inStock = inStock - quantity;
				cash = bookRetailerDB.getCash();
				payment += bookPrice * quantity;
				cash = cash + payment;
				bookRetailerDB.updateInstock(bookName, inStock);
				bookRetailerDB.updateCash(cash);
				soldBooks += bookName + "#";
			}
		}
		receiptFormHash.put("Exception", "Normal");
		receiptFormHash.put("AccountName", accountName);
		receiptFormHash.put("BookName", soldBooks);
		receiptFormHash.put("Quantity", tools.convertIntToString(quantity));
		receiptFormHash.put("Payment", tools.convertFloatToString(payment));
		receiptFormHash.put("CreditCardNumber", inputCreditCardNumber);
		return receiptFormHash;
	}
}