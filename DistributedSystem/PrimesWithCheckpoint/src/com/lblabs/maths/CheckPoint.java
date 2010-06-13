package com.lblabs.maths;

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
import com.lblabs.xmltool.SelectorForHash;

class CheckPoint
{
	long DEFAULT_STOP;
	int firstPrime;
	int MAX;
	int currentPrimeCount;
	long[] currentPrimeArray;
	String checkPointXML;

	public CheckPoint()
	{
		this.checkPointXML = "checkpoint.xml";
		String currentDir = System.getProperty("user.dir") + "/db/";
		this.checkPointXML = currentDir + this.checkPointXML;
	}

	public long getDEFAULT_STOP()
	{
		Tools tools = new Tools();
		SelectorByKeyTag selectorByKeyTag = new SelectorByKeyTag();
		DEFAULT_STOP = tools.convertStringToLong(selectorByKeyTag
				.selectByKeyTag(checkPointXML, "DefaultStop"));
		return DEFAULT_STOP;
	}

	public int getFirstPrime()
	{
		Tools tools = new Tools();
		SelectorByKeyTag selectorByKeyTag = new SelectorByKeyTag();
		firstPrime = tools.convertStringToInt(selectorByKeyTag.selectByKeyTag(
				checkPointXML, "FirstPrime"));
		return firstPrime;
	}

	public int getMAX()
	{
		Tools tools = new Tools();
		SelectorByKeyTag selectorByKeyTag = new SelectorByKeyTag();
		MAX = tools.convertStringToInt(selectorByKeyTag.selectByKeyTag(
				checkPointXML, "MaxCount"));
		return MAX;
	}

	public int getCurrentPrimeCount()
	{
		Tools tools = new Tools();
		SelectorByKeyTag selectorByKeyTag = new SelectorByKeyTag();
		currentPrimeCount = tools.convertStringToInt(selectorByKeyTag
				.selectByKeyTag(checkPointXML, "CurrentPrimeCount"));
		return currentPrimeCount;
	}

	public long[] getCurrentPrimes()
	{
		SelectorForHash selectorForHash = new SelectorForHash();
		Hashtable currentPrimesHash = new Hashtable();
		currentPrimesHash = selectorForHash.selectHash(checkPointXML,
				"CurrentPrime");
		Tools tools = new Tools();
		long[] currentPrimes = new long[currentPrimesHash.size()];
		String str = null;
		for (int i = 0; i < currentPrimesHash.size(); i++)
		{
			currentPrimes[i] = tools
					.convertStringToLong((String) currentPrimesHash.get(str
							.valueOf(i)));
		}
		return currentPrimes;
	}

	public void increaseStopBy(long steps)
	{
		Tools tools = new Tools();
		LBXMLOperator lbXMLOperator = new LBXMLOperator();
		long stop = this.getDEFAULT_STOP();
		lbXMLOperator.changeByTag(checkPointXML, "DefaultStop", tools
				.convertLongToString(stop + steps));
	}

	public boolean createCheckPoint(long currentPrime)
	{
		Tools tools = new Tools();
		LBXMLOperator lbXMLOperator = new LBXMLOperator();
		lbXMLOperator.changeByTag(checkPointXML, "FirstPrime", tools
				.convertLongToString(currentPrime));

		int currentPrimeCountToBeUpdated = this.getCurrentPrimeCount();
		lbXMLOperator.changeByTag(checkPointXML, "CurrentPrimeCount", tools
				.convertIntToString(++currentPrimeCountToBeUpdated));

		try
		{
			InputStream is = new FileInputStream(checkPointXML);
			Parser parser = new Parser(checkPointXML);

			Document doc = parser.readStream(is);
			Element root = doc.getDocumentElement();

			Element currentPrimeItem;

			currentPrimeItem = doc.createElement("CurrentPrime");
			currentPrimeItem.appendChild(doc.createTextNode(tools
					.convertLongToString(currentPrime)));
			root.appendChild(currentPrimeItem);

			FileWriter fw = new FileWriter(checkPointXML);
			PrintWriter pw = new PrintWriter(fw, true);

			((TXDocument) doc).printWithFormat(pw);
			return true;
		} catch (Exception e)
		{
			e.printStackTrace();
			return false;
		}
	}
}
