package com.lblabs.parser;

import java.io.*;
import java.util.*;

public class ParseIDL
{
	public static void main(String args[])
	{
		SimplifiedParser simplifiedParser = new SimplifiedParser();
		ArrayList methodList = new ArrayList();
//		methodList = simplifiedParser.getMethodList("./RetailerRemote.java");
		methodList = simplifiedParser.getMethodList("./BankInterface.java");
		int size = methodList.size();
		for (int i = 0; i < size; i ++)
		{
System.out.println("\"" + methodList.get(i) + "\"");
		}

System.out.println("\n");
		ArrayList parameterList = new ArrayList();
//		parameterList = simplifiedParser.getParameterList("./RetailerRemote.java", "updatePCPrice");
		parameterList = simplifiedParser.getParameterList("./BankInterface.java", "deposit");
		size = parameterList.size();
		for (int i = 0; i < size; i ++)
		{
System.out.println("\"" + parameterList.get(i) + "\"");
		}

//		parameterList = simplifiedParser.getParameterList("./RetailerRemote.java", "initialize");
		parameterList = simplifiedParser.getParameterList("./BankInterface.java", "withdraw");
		size = parameterList.size();
		for (int i = 0; i < size; i ++)
		{
System.out.println("\"" + parameterList.get(i) + "\"");
		}

//		parameterList = simplifiedParser.getParameterList("./RetailerRemote.java", "getAllPC");
		parameterList = simplifiedParser.getParameterList("./BankInterface.java", "transfer");
		size = parameterList.size();
		for (int i = 0; i < size; i ++)
		{
System.out.println("\"" + parameterList.get(i) + "\"");
		}

		ArrayList returnValueList = new ArrayList();
//		returnValueList = simplifiedParser.getReturnValueList("./RetailerRemote.java", "getAllPC");
		returnValueList = simplifiedParser.getReturnValueList("./BankInterface.java", "deposit");
		size = returnValueList.size();
		for (int i = 0; i < size; i ++)
		{
System.out.println("\"" + returnValueList.get(i) + "\"");
		}

//		returnValueList = simplifiedParser.getReturnValueList("./RetailerRemote.java", "initialize");
		returnValueList = simplifiedParser.getReturnValueList("./BankInterface.java", "withdraw");
		size = returnValueList.size();
		for (int i = 0; i < size; i ++)
		{
System.out.println("\"" + returnValueList.get(i) + "\"");
		}

//		returnValueList = simplifiedParser.getReturnValueList("./RetailerRemote.java", "updatePCPrice");
		returnValueList = simplifiedParser.getReturnValueList("./BankInterface.java", "transfer");
		size = returnValueList.size();
		for (int i = 0; i < size; i ++)
		{
System.out.println("\"" + returnValueList.get(i) + "\"");
		}
	}
}
