package com.lblabs.net;

import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;
import java.net.Socket;
import java.util.Hashtable;

public class BookRetailer
{
	Socket socket;
	String host = "127.0.0.1";
	int port = 8888;
	int size = 1024;
	byte[] buffer = new byte[size];
	String threadID;
	String request;
	String response;

	public BookRetailer()
	{
		try
		{
			socket = new Socket(host, port, false);
		} catch (IOException e)
		{
			System.out.println(e);
		}
	}

	public boolean isConnectionAvailable(String threadID)
	{
		this.threadID = threadID;
		request = threadID + "|isConnectionAvailable|" + threadID + "|";
		response = this.sendRequest(request);
		return this.convertStringToBoolean(response);
	}

	public boolean signIn(String threadID)
	{
		this.threadID = threadID;
		request = threadID + "|signIn|" + threadID + "|";
		response = this.sendRequest(request);
		return this.convertStringToBoolean(response);
	}

	public boolean signOut(String threadID)
	{
		request = threadID + "|signOut|" + threadID + "|";
		response = this.sendRequest(request);
		return this.convertStringToBoolean(response);
	}

	public float getPrice(String bookName)
	{
		request = threadID + "|getPrice|" + bookName + "|";
		response = this.sendRequest(request);
		if (response != null)
		{
			return this.convertStringToFloat(response);
		} else
		{
			return -1;
		}
	}

	public String getPublisher(String bookName)
	{
		request = threadID + "|getPublisher|" + bookName + "|";
		response = this.sendRequest(request);
		return response;
	}

	public String getAuthor(String bookName)
	{
		request = threadID + "|getAuthor|" + bookName + "|";
		response = this.sendRequest(request);
		return response;
	}

	public Hashtable buyBooks(String inputAccountName,
			String inputAccountPassword, String inputCreditCardNumber,
			String inputBookName, int inputQuantity)
	{
		request = threadID + "|buyBooks|" + inputAccountName + "#"
				+ inputAccountPassword + "#" + inputCreditCardNumber + "#"
				+ inputBookName + "#" + this.convertIntToString(inputQuantity)
				+ "#|";
		response = this.sendRequest(request);
		Hashtable responseHash = this.convertStringToHash(response);
		return responseHash;
	}

	private String sendRequest(String request)
	{
		try
		{
			InputStream in = socket.getInputStream();
			OutputStream out = socket.getOutputStream();

			buffer = request.getBytes();

			out.write(buffer, 0, request.length());
			out.flush();

			buffer = new byte[size];

			int length = in.read(buffer);
			response = new String(buffer);
			int index = response.lastIndexOf("#");
			return response.substring(0, index);
		} catch (IOException e)
		{
			return null;
		}
	}

	private Hashtable convertStringToHash(String strValue)
	{
		int num = 0;
		int index = strValue.indexOf("#");
		Hashtable hashValue = new Hashtable();
		// String str = null;
		while (index >= 0)
		{
			hashValue.put(String.valueOf(num++), strValue.substring(0, index));
			strValue = strValue.substring(index + 1);
			index = strValue.indexOf("#");
		}
		return hashValue;
	}

	private String convertIntToString(int intValue)
	{
		// String str = null;
		return String.valueOf(intValue);
	}

	private float convertStringToFloat(String floatString)
	{
		Float bigFloat = new Float(floatString);
		return bigFloat.floatValue();
	}

	private boolean convertStringToBoolean(String boolString)
	{
		Boolean bigBool = new Boolean(boolString);
		return bigBool.booleanValue();
	}
}
