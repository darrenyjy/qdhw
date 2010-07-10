package com.lblabs.web;

import java.io.*;

import java.lang.StringBuffer;

import java.util.Hashtable;

import com.lblabs.tools.Tools;
import com.lblabs.xmltool.SelectorByKeyTag;
import com.lblabs.xmltool.SelectorByTagAndWhere;
import com.lblabs.xmltool.SelectorForHash;
import com.lblabs.xmltool.LBXMLOperator;

import com.ibm.xml.parser.TXDocument;
import com.ibm.xml.parser.Parser;
import org.w3c.dom.Document;
import org.w3c.dom.Element;

public class WebServer
{
	String usrDir;
	String webDir;
	String webConfigXML;
	String webDomainName;
	String initResponseXML;
	String responseXML;
	public WebServer()
	{
		usrDir = System.getProperty("user.dir") + "/db/";
		webConfigXML = this.usrDir + "web_config.xml";
		SelectorByKeyTag selectorByKeyTag = new SelectorByKeyTag();
		webDomainName = selectorByKeyTag.selectByKeyTag(webConfigXML, "WebDomainName");
		webDir = System.getProperty("user.dir") + selectorByKeyTag.selectByKeyTag(webConfigXML, "WebDir");
		initResponseXML = webDir + "init_response.xml";
		responseXML = webDir + "response.xml";
	}

	public String getResponseXML()
	{
		Tools tools = new Tools();
		tools.cp(initResponseXML, responseXML);
		return responseXML;
	}

	public StringBuffer getPage(String pageName)
	{
		if (pageName.equals("default"))
		{
			pageName = "index.xml";
		}
		StringBuffer stringBuffer = new StringBuffer(); 
		stringBuffer.append(pageName + "|");
		String actualPage = webDir + pageName;
System.out.println("actualPage = " + actualPage);

		Tools tools = new Tools();
		stringBuffer.append(tools.getTextFileBuffer(actualPage));
		return stringBuffer;
	}
}