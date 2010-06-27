package com.lblabs.tools;

import java.io.*;
import java.util.Hashtable;
import java.util.Enumeration;

public class Tools 
{
	public String convertHashToString(Hashtable hashValue)
	{
		String strValue = "";
		Enumeration enumx = hashValue.keys();
		String key;
		while (enumx.hasMoreElements())
		{
			key = (String)enumx.nextElement();
			strValue += (String)hashValue.get(key) + "#";
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
			hashValue.put(str.valueOf(num ++), strValue.substring(0, index));
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

	private boolean convertStringToBoolean(String boolString)
	{
		Boolean bigBool = new Boolean(boolString);
		return bigBool.booleanValue();
	}

	public void cp(String source, String target)
	{
		String str;
		try
		{
			BufferedReader br = new BufferedReader(new FileReader(source));
			BufferedWriter bw = new BufferedWriter(new FileWriter(target));

			while ((str = br.readLine()) != null)
			{
				bw.write(str + System.getProperty(("line.separator")));
			}
			bw.flush();
			br.close();
			bw.close();
		}
		catch (FileNotFoundException fnfe)
		{
			System.out.println(fnfe);
			return;
		}
		catch (IOException ioe)
		{
			System.out.println(ioe);
		}
	}

	public void wrxml(String xmlFile, StringBuffer xmlBuffer)
	{
		String str = null;
		try
		{
			BufferedWriter bw = new BufferedWriter(new FileWriter(xmlFile));
			str = xmlBuffer.toString();
			bw.write(str);
			bw.flush();
			bw.close();
		}
		catch (FileNotFoundException fnfe)
		{
			System.out.println(fnfe);
		}
		catch (IOException ioe)
		{
			System.out.println(ioe);
		}
	}

	public StringBuffer getTextFileBuffer(String fileName)
	{
		StringBuffer stringBuffer = new StringBuffer();
		String str = null;
		try
		{
			BufferedReader br = new BufferedReader(new FileReader(fileName));
			while ((str = br.readLine()) != null)
			{
				stringBuffer.append(str + System.getProperty("line.separator"));
			}
			br.close();
			return stringBuffer;
		}
		catch (FileNotFoundException fnfe)
		{
			System.out.println(fnfe);
			stringBuffer.append("Error");
			return stringBuffer;
		}
		catch (IOException ioe)
		{
			System.out.println(ioe);
			stringBuffer.append("Error");
			return stringBuffer;
		}
	}
}
