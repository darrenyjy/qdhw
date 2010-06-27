package com.lblabs.business;

import java.io.*;
import java.util.Hashtable;

import com.lblabs.xmltool.SelectorByTagAndWhere;
import com.lblabs.xmltool.SelectorForHash;
import com.lblabs.xmltool.SelectorByKeyTag;
import com.lblabs.xmltool.LBXMLOperator;

import com.lblabs.tools.Tools;

import com.ibm.xml.parser.TXDocument;
import com.ibm.xml.parser.Parser;
import org.w3c.dom.Document;
import org.w3c.dom.Element;

class BookRetailerDB 
{
	String bookStoreXML;
	String accountInfoXML;
	String requestQueueXML;
	String responseQueueXML;
	String usrDir;
	Tools tools = new Tools();

	public BookRetailerDB()
	{
		usrDir = System.getProperty("user.dir") + "/db/";
		this.bookStoreXML = usrDir + "book_store.xml";
		this.accountInfoXML = usrDir + "account_info.xml";
		this.requestQueueXML = usrDir + "request_queue.xml";
		this.responseQueueXML = usrDir + "response_queue.xml";
	}

	public float getPrice(String bookName)
	{
		SelectorByTagAndWhere selectorByTagAndWhere = new SelectorByTagAndWhere();
		float bookPrice = tools.convertStringToFloat(selectorByTagAndWhere.selectByTagAndWhere(bookStoreXML, "BookName", "BookPrice", bookName));
		return bookPrice;
	}

	public String getPublisher(String bookName)
	{
		SelectorByTagAndWhere selectorByTagAndWhere = new SelectorByTagAndWhere();
		String publisher = selectorByTagAndWhere.selectByTagAndWhere(bookStoreXML, "BookName", "BookPublisher", bookName);
		return publisher;
	}

	public String getAuthor(String bookName)
	{
		SelectorByTagAndWhere selectorByTagAndWhere = new SelectorByTagAndWhere();
		String author = selectorByTagAndWhere.selectByTagAndWhere(bookStoreXML, "BookName", "BookAuthor", bookName);
		return author;
	}

	public boolean isAccountNameExisted(String inputAccountName)
	{
		SelectorForHash selectorForHash = new SelectorForHash();
		Hashtable accountNameHash = new Hashtable();
		accountNameHash = selectorForHash.selectHash(accountInfoXML, "AccountName");
		if (!accountNameHash.containsValue(inputAccountName))
		{
			return false;
		}
		else
		{
			return true;
		}
	}

	public boolean saveAccount(String newAccountName, String newAccountPassword, String newCreditCardNumber)
	{
		boolean isSignUpSuccessful = false;
		try
		{
			InputStream is = new FileInputStream(accountInfoXML);
			Parser parser = new Parser(accountInfoXML);

			Document doc = parser.readStream(is);
			Element root = doc.getDocumentElement();

			Element accountItem;
			Element accountNameItem;
			Element accountPasswordItem;
			Element creditCardNumberItem;

			accountItem = doc.createElement("Account");
			root.appendChild(accountItem);

			accountNameItem = doc.createElement("AccountName");
			accountNameItem.appendChild(doc.createTextNode(newAccountName));
			accountItem.appendChild(accountNameItem);

			accountPasswordItem = doc.createElement("AccountPassword");
			accountPasswordItem.appendChild(doc.createTextNode(newAccountPassword));
			accountItem.appendChild(accountPasswordItem);

			creditCardNumberItem = doc.createElement("CreditCardNumber");
			creditCardNumberItem.appendChild(doc.createTextNode(newCreditCardNumber));
			accountItem.appendChild(creditCardNumberItem);

			FileWriter fw = new FileWriter(accountInfoXML);
			PrintWriter pw = new PrintWriter(fw, true);

			((TXDocument)doc).printWithFormat(pw);

			isSignUpSuccessful = true;
		}
		catch (Exception e)
		{
			e.printStackTrace();
			isSignUpSuccessful = false;
		}
		return isSignUpSuccessful;
	}

	public boolean isAccountMatched(String inputAccountName, String inputAccountPassword)
	{
		SelectorForHash selectorForHash = new SelectorForHash();
		SelectorByTagAndWhere selectorByTagAndWhere = new SelectorByTagAndWhere();
		Hashtable accountNameHash = selectorForHash.selectHash(accountInfoXML, "AccountName");
		if (accountNameHash.containsValue(inputAccountName))
		{
			String accountPassword = selectorByTagAndWhere.selectByTagAndWhere(accountInfoXML, "AccountName", "AccountPassword", inputAccountName);
			if (inputAccountPassword.equals(accountPassword))
			{
				return true;
			}
			else
			{
				return false;
			}
		}
		else
		{
			return false;
		}
	}

	public boolean isBookExisted(String bookName)
	{
		SelectorForHash selectorForHash = new SelectorForHash();
		Hashtable bookNameHash = new Hashtable();
		bookNameHash = selectorForHash.selectHash(bookStoreXML, "BookName");
		if (bookNameHash.containsValue(bookName))
		{
			return true;
		}
		else
		{
			return false;
		}
	}

	public boolean isCreditCardExisted(String accountName, String inputCreditCardNumber)
	{
		SelectorByTagAndWhere selectorByTagAndWhere = new SelectorByTagAndWhere();
		String creditCardNumber = selectorByTagAndWhere.selectByTagAndWhere(accountInfoXML, "AccountName", "CreditCardNumber", accountName);
		if (inputCreditCardNumber.equals(creditCardNumber))
		{
			return true;
		}
		else
		{
			return false;
		}
	}

	public int getInstock(String bookName)
	{
		SelectorByTagAndWhere selectorByTagAndWhere = new SelectorByTagAndWhere();
		return tools.convertStringToInt(selectorByTagAndWhere.selectByTagAndWhere(bookStoreXML, "BookName", "BookInStock", bookName));
	}

	public float getCash()
	{
		SelectorByKeyTag selectorByKeyTag = new SelectorByKeyTag();
		return tools.convertStringToFloat(selectorByKeyTag.selectByKeyTag(bookStoreXML, "Cash"));
	}

	public void updateInstock(String bookName, int inStock)
	{
		LBXMLOperator lbXMLOperator = new LBXMLOperator();
		Hashtable keyTagHash = new Hashtable();
		Hashtable keyValueHash = new Hashtable();
		keyTagHash.put("0", "BookName");
		keyTagHash.put("1", "BookInStock");
		keyValueHash.put("0", bookName);
		keyValueHash.put("1", tools.convertIntToString(inStock));
		lbXMLOperator.changeByMultipleTagsAndWhere(bookStoreXML, keyTagHash, keyValueHash);
	}

	public void updateCash(float cash)
	{
		LBXMLOperator lbXMLOperator = new LBXMLOperator();
		lbXMLOperator.changeByTag(bookStoreXML, "Cash", tools.convertFloatToString(cash));
	}
}
