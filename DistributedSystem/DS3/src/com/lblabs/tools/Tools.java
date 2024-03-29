package com.lblabs.tools;

import java.util.Enumeration;
import java.util.Hashtable;

public class Tools
{
	public String convertHashToString(Hashtable hashValue)
	{
		String strValue = "";
		Enumeration enumeration = hashValue.keys();
		while (enumeration.hasMoreElements())
		{
			strValue += (String) hashValue.get((String) enumeration
					.nextElement())
					+ "#";
		}
		return strValue;
	}

	public Hashtable convertStringToHash(String strValue)
	{
		int num = 0;
		int index = strValue.indexOf("#");
		Hashtable hashValue = new Hashtable();
		String str = null;
		while (index >= 0)
		{
			hashValue.put(str.valueOf(num++), strValue.substring(0, index));
			strValue = strValue.substring(index + 1);
			index = strValue.indexOf("#");
		}
		return hashValue;
	}

	public String convertBooleanToString(boolean boolValue)
	{
		String str = null;
		return str.valueOf(boolValue);
	}

	public float convertStringToFloat(String floatStr)
	{
		Float bigFloat = new Float(floatStr);
		return bigFloat.floatValue();
	}

	public int convertStringToInt(String intStr)
	{
		Integer bigInt = new Integer(intStr);
		return bigInt.intValue();
	}

	public String convertIntToString(int intValue)
	{
		String str = null;
		return str.valueOf(intValue);
	}

	public String convertFloatToString(float floatValue)
	{
		String str = null;
		return str.valueOf(floatValue);
	}
}
