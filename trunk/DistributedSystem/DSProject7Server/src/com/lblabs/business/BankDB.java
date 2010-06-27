package com.lblabs.business;

import java.util.Hashtable;

import com.lblabs.xmltool.SelectorByKeyTag;
import com.lblabs.xmltool.SelectorByTagAndWhere;
import com.lblabs.xmltool.LBXMLOperator;
import com.lblabs.tools.Tools;

class BankDB 
{
	String usrDir;
	String bankXML;
	Tools tools = new Tools();

	public BankDB()
	{
		usrDir = System.getProperty("user.dir") + "/db/";
		this.bankXML = usrDir + "bank.xml";
	}

	public float getTotalDeposit()
	{
		SelectorByKeyTag selectorByKeyTag = new SelectorByKeyTag();
		return tools.convertStringToFloat(selectorByKeyTag.selectByKeyTag(bankXML, "TotalDeposit"));
	}

	public float getAccountBalance(String accountName)
	{
		SelectorByTagAndWhere selectorByTagAndWhere = new SelectorByTagAndWhere();
		return tools.convertStringToFloat(selectorByTagAndWhere.selectByTagAndWhere(bankXML, "AccountName", "AccountBalance", accountName));
	}

	public void saveBalance(String accountName, float accountBalance)
	{
		LBXMLOperator lbXMLOperator = new LBXMLOperator();
		Hashtable keyTagHash = new Hashtable();
		Hashtable keyValueHash = new Hashtable();
		keyTagHash.put("0", "AccountName");
		keyTagHash.put("1", "AccountBalance");
		keyValueHash.put("0", accountName);
		keyValueHash.put("1", tools.convertFloatToString(accountBalance));
		lbXMLOperator.changeByMultipleTagsAndWhere(bankXML, keyTagHash, keyValueHash);
	}

	public void saveTotalDeposit(float totalDeposit)
	{
		LBXMLOperator lbXMLOperator = new LBXMLOperator();
		lbXMLOperator.changeByTag(bankXML, "TotalDeposit", tools.convertFloatToString(totalDeposit));
	}
}
