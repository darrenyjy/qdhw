package com.lblabs.dns;

import java.io.InputStream;
import java.io.FileInputStream;
import java.io.PrintWriter;
import java.io.FileWriter;

import java.util.Hashtable;
import com.lblabs.xmltool.SelectorByKeyTag;
import com.lblabs.xmltool.SelectorByTagAndWhere;
import com.lblabs.xmltool.SelectorForHash;
import com.lblabs.xmltool.LBXMLOperator;

import com.ibm.xml.parser.TXDocument;
import com.ibm.xml.parser.Parser;
import org.w3c.dom.Document;
import org.w3c.dom.Element;

public class RegistryServer
{
	String dnsRegistryXML;

	public RegistryServer()
	{
		String usrDir = System.getProperty("user.dir") + "/db/";
		dnsRegistryXML = usrDir + "dns_registry.xml";
	}

	public boolean registerDNS(String yourURL, String yourAddress)
	{
		try
		{
			InputStream is = new FileInputStream(dnsRegistryXML);
			Parser parser = new Parser(dnsRegistryXML);

			Document doc = parser.readStream(is);
			Element root = doc.getDocumentElement();

			Element dnsContentItem;
			Element urlItem;
			Element addressItem;

			dnsContentItem = doc.createElement("DNSContent");
			root.appendChild(dnsContentItem);

			urlItem = doc.createElement("URL");
			urlItem.appendChild(doc.createTextNode(yourURL));
			dnsContentItem.appendChild(urlItem);

			addressItem = doc.createElement("Address");
			addressItem.appendChild(doc.createTextNode(yourAddress));
			dnsContentItem.appendChild(addressItem);

			FileWriter fw = new FileWriter(dnsRegistryXML);
			PrintWriter pw = new PrintWriter(fw, true);

			((TXDocument)doc).printWithFormat(pw);
			return true;
		}
		catch (Exception e)
		{
			e.printStackTrace();
			return false;
		}
	}

	public String searchAddress(String domainName)
	{
		SelectorByTagAndWhere selectorByTagAndWhere = new SelectorByTagAndWhere();
		return selectorByTagAndWhere.selectByTagAndWhere(dnsRegistryXML, "URL", "Address", domainName);
	}
}
