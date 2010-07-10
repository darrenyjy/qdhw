package com.lblabs.parser;

import java.io.*;
import java.util.*;

public class SimplifiedParser
{
	int blankIndex = 0;
	public ArrayList getMethodList(String javaInterfaceFile)
	{
		ArrayList methodList = new ArrayList();
		StringBuffer interfaceBuffer = new StringBuffer();
		interfaceBuffer = this.getInterfaceBuffer(javaInterfaceFile);

		int startIndex = interfaceBuffer.indexOf("{") + 1;
		int endIndex = interfaceBuffer.indexOf("}");
		String javaBody = interfaceBuffer.substring(startIndex, endIndex);

		int methodSize = 0;
		int index = javaBody.indexOf(";");
		String methodLine;
		int startMethodLineIndex;
		int endMethodLineIndex;
		while (index >= 0)
		{
			methodLine = javaBody.substring(0, index);
			startMethodLineIndex = methodLine.indexOf("public") + 6;
			endMethodLineIndex = methodLine.indexOf("(");
			methodLine = methodLine.substring(startMethodLineIndex, endMethodLineIndex);
			endMethodLineIndex = methodLine.lastIndexOf(" ");
			methodLine = methodLine.substring(endMethodLineIndex + 1);
			methodList.add(methodSize ++, methodLine);
			javaBody = javaBody.substring(index + 1);
			index = javaBody.indexOf(";");
		}

		return methodList;
	}

	public ArrayList getParameterList(String javaInterfaceFile, String methodName)
	{
		ArrayList parameterList = new ArrayList();
		ArrayList parameterNameList = new ArrayList();
		ArrayList parameterTypeList = new ArrayList();
		StringBuffer interfaceBuffer = new StringBuffer();
		interfaceBuffer = this.getInterfaceBuffer(javaInterfaceFile);
		int startParameterIndex = interfaceBuffer.indexOf(methodName);
		String parameterLine = interfaceBuffer.substring(startParameterIndex);
		startParameterIndex = parameterLine.indexOf(")") + 1;
		parameterLine = parameterLine.substring(0, startParameterIndex);

		startParameterIndex = parameterLine.indexOf("(") + 1;
		int endParameterIndex = parameterLine.indexOf(")");
		parameterLine = parameterLine.substring(startParameterIndex, endParameterIndex);
		int index = parameterLine.indexOf(",");
		String parameter;
		String parameterType;
		String parameterName;
		int parameterIndex = 0;
		while (index >= 0)
		{
			parameter = parameterLine.substring(0, index);
			parameter = this.removeFrontBlank(parameter);
			parameterName = parameter.substring(blankIndex + 1);
			parameterType = parameter.substring(0, blankIndex);
			parameterName = this.removeFrontBlank(parameterName);
			parameterName = this.removeBackBlank(parameterName);
			parameterNameList.add(parameterIndex, parameterName);
			parameterTypeList.add(parameterIndex ++, parameterType);
			parameterLine = parameterLine.substring(index + 1);
			index = parameterLine.indexOf(",");
		}

		parameterLine = this.removeFrontBlank(parameterLine);
		if (blankIndex > 0)
		{
			parameterName = parameterLine.substring(blankIndex + 1);
			parameterType = parameterLine.substring(0, blankIndex);

			parameterName = this.removeFrontBlank(parameterName);
			parameterName = this.removeBackBlank(parameterName);
			parameterNameList.add(parameterIndex, parameterName);
			parameterTypeList.add(parameterIndex ++, parameterType);
		}

		parameterList.add(0, parameterNameList);
		parameterList.add(1, parameterTypeList);
		return parameterList;
	}

	public ArrayList getReturnValueList(String javaInterfaceFile, String methodName)
	{
		ArrayList returnValueList = new ArrayList();
		String returnValue;
		int returnValueSize = 0;
		StringBuffer interfaceBuffer = new StringBuffer();
		interfaceBuffer = this.getInterfaceBuffer(javaInterfaceFile);
		int returnValueIndex = interfaceBuffer.indexOf(methodName);
		String returnValueLine = interfaceBuffer.substring(0, returnValueIndex);
		returnValueIndex = returnValueLine.indexOf("{");
		returnValueLine = returnValueLine.substring(returnValueIndex + 1);
		returnValueIndex = returnValueLine.lastIndexOf(";");
		if (returnValueIndex >= 0)
		{
			returnValueLine = returnValueLine.substring(returnValueIndex + 1);
		}

		returnValueLine = this.removeFrontBlank(returnValueLine);
		if (blankIndex > 0)
		{
			returnValue = returnValueLine.substring(blankIndex + 1);
			returnValue = this.removeFrontBlank(returnValue);
			returnValue = this.removeBackBlank(returnValue);
			returnValueList.add(returnValueSize ++, returnValue);
		}
		return returnValueList;
	}

	private String removeFrontBlank(String stringValue)
	{
		blankIndex = stringValue.indexOf(" ");
		while (blankIndex == 0)
		{
			stringValue = stringValue.substring(blankIndex + 1);
			blankIndex = stringValue.indexOf(" ");
		}
		return stringValue;
	}

	private String removeBackBlank(String stringValue)
	{
		int blankIndex = stringValue.lastIndexOf(" ");
		while (blankIndex >= 0)
		{
			stringValue = stringValue.substring(0, blankIndex);
			blankIndex = stringValue.lastIndexOf(" ");
		}
		return stringValue;
	}

	private StringBuffer getInterfaceBuffer(String javaInterfaceFile)
	{
		String str;
		StringBuffer interfaceBuffer = new StringBuffer();
		try
		{
			BufferedReader br = new BufferedReader(new FileReader(javaInterfaceFile));
			while ((str = br.readLine()) != null)
			{
				interfaceBuffer.append(str);
			}
			br.close();
		}
		catch (FileNotFoundException fnfe)
		{
			System.out.println(fnfe);
			return null;
		}
		catch (IOException ioe)
		{
			System.out.println(ioe);
			return null;
		}
		return interfaceBuffer;
	}
}
