/**
 * 
 */
package com.lblabs.business;

/**
 * @author Coddy created on 2010-7-10
 *
 */
import java.io.FileInputStream;
import java.io.FileWriter;
import java.io.InputStream;
import java.io.PrintWriter;
import java.util.Hashtable;

import org.w3c.dom.Document;
import org.w3c.dom.Element;

import com.ibm.xml.parser.Parser;
import com.ibm.xml.parser.TXDocument;
import com.lblabs.tools.Tools;
import com.lblabs.xmltool.LBXMLOperator;
import com.lblabs.xmltool.SelectorByKeyTag;
import com.lblabs.xmltool.SelectorByTagAndWhere;
import com.lblabs.xmltool.SelectorForHash;

class MovieRetailerDB
{
	String movieStoreXML;
	String accountInfoXML;
	String requestQueueXML;
	String responseQueueXML;
	String usrDir;
	Tools tools = new Tools();

	public MovieRetailerDB()
	{
		usrDir = System.getProperty("user.dir") + "/db/";
		this.movieStoreXML = usrDir + "movie_store.xml";
		this.accountInfoXML = usrDir + "account_info.xml";
		this.requestQueueXML = usrDir + "request_queue.xml";
		this.responseQueueXML = usrDir + "response_queue.xml";
	}

	public float getPrice(String movieName)
	{
		SelectorByTagAndWhere selectorByTagAndWhere = new SelectorByTagAndWhere();
		float moviePrice = tools.convertStringToFloat(selectorByTagAndWhere
				.selectByTagAndWhere(movieStoreXML, "MovieName", "MoviePrice",
						movieName));
		return moviePrice;
	}

	public String getPublisher(String movieName)
	{
		SelectorByTagAndWhere selectorByTagAndWhere = new SelectorByTagAndWhere();
		String publisher = selectorByTagAndWhere.selectByTagAndWhere(
				movieStoreXML, "MovieName", "MoviePublisher", movieName);
		return publisher;
	}

	public String getAuthor(String movieName)
	{
		SelectorByTagAndWhere selectorByTagAndWhere = new SelectorByTagAndWhere();
		String author = selectorByTagAndWhere.selectByTagAndWhere(
				movieStoreXML, "MovieName", "MovieAuthor", movieName);
		return author;
	}

	public boolean isAccountNameExisted(String inputAccountName)
	{
		SelectorForHash selectorForHash = new SelectorForHash();
		Hashtable accountNameHash = new Hashtable();
		accountNameHash = selectorForHash.selectHash(accountInfoXML,
				"AccountName");
		if (!accountNameHash.containsValue(inputAccountName))
		{
			return false;
		} else
		{
			return true;
		}
	}

	public boolean saveAccount(String newAccountName,
			String newAccountPassword, String newCreditCardNumber)
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
			accountPasswordItem.appendChild(doc
					.createTextNode(newAccountPassword));
			accountItem.appendChild(accountPasswordItem);

			creditCardNumberItem = doc.createElement("CreditCardNumber");
			creditCardNumberItem.appendChild(doc
					.createTextNode(newCreditCardNumber));
			accountItem.appendChild(creditCardNumberItem);

			FileWriter fw = new FileWriter(accountInfoXML);
			PrintWriter pw = new PrintWriter(fw, true);

			((TXDocument) doc).printWithFormat(pw);

			isSignUpSuccessful = true;
		} catch (Exception e)
		{
			e.printStackTrace();
			isSignUpSuccessful = false;
		}
		return isSignUpSuccessful;
	}

	public boolean isAccountMatched(String inputAccountName,
			String inputAccountPassword)
	{
		SelectorForHash selectorForHash = new SelectorForHash();
		SelectorByTagAndWhere selectorByTagAndWhere = new SelectorByTagAndWhere();
		Hashtable accountNameHash = selectorForHash.selectHash(accountInfoXML,
				"AccountName");
		if (accountNameHash.containsValue(inputAccountName))
		{
			String accountPassword = selectorByTagAndWhere.selectByTagAndWhere(
					accountInfoXML, "AccountName", "AccountPassword",
					inputAccountName);
			if (inputAccountPassword.equals(accountPassword))
			{
				return true;
			} else
			{
				return false;
			}
		} else
		{
			return false;
		}
	}

	public boolean isMovieExisted(String movieName)
	{
		SelectorForHash selectorForHash = new SelectorForHash();
		Hashtable movieNameHash = new Hashtable();
		movieNameHash = selectorForHash.selectHash(movieStoreXML, "MovieName");
		if (movieNameHash.containsValue(movieName))
		{
			return true;
		} else
		{
			return false;
		}
	}

	public boolean isCreditCardExisted(String accountName,
			String inputCreditCardNumber)
	{
		SelectorByTagAndWhere selectorByTagAndWhere = new SelectorByTagAndWhere();
		String creditCardNumber = selectorByTagAndWhere.selectByTagAndWhere(
				accountInfoXML, "AccountName", "CreditCardNumber", accountName);
		if (inputCreditCardNumber.equals(creditCardNumber))
		{
			return true;
		} else
		{
			return false;
		}
	}

	public int getInstock(String movieName)
	{
		SelectorByTagAndWhere selectorByTagAndWhere = new SelectorByTagAndWhere();
		return tools.convertStringToInt(selectorByTagAndWhere
				.selectByTagAndWhere(movieStoreXML, "MovieName",
						"MovieInStock", movieName));
	}

	public float getCash()
	{
		SelectorByKeyTag selectorByKeyTag = new SelectorByKeyTag();
		return tools.convertStringToFloat(selectorByKeyTag.selectByKeyTag(
				movieStoreXML, "Cash"));
	}

	public void updateInstock(String movieName, int inStock)
	{
		LBXMLOperator lbXMLOperator = new LBXMLOperator();
		Hashtable keyTagHash = new Hashtable();
		Hashtable keyValueHash = new Hashtable();
		keyTagHash.put("0", "MovieName");
		keyTagHash.put("1", "MovieInStock");
		keyValueHash.put("0", movieName);
		keyValueHash.put("1", tools.convertIntToString(inStock));
		lbXMLOperator.changeByMultipleTagsAndWhere(movieStoreXML, keyTagHash,
				keyValueHash);
	}

	public void updateCash(float cash)
	{
		LBXMLOperator lbXMLOperator = new LBXMLOperator();
		lbXMLOperator.changeByTag(movieStoreXML, "Cash", tools
				.convertFloatToString(cash));
	}
}
