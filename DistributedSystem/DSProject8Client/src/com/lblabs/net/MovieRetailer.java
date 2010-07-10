package com.lblabs.net;

import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;
import java.net.Socket;
import java.util.Hashtable;

import com.lblabs.tools.Tools;

public class MovieRetailer
{
	Socket socket;
	String host = "127.0.0.1";
	int port = 8888;
	int size = 1024;
	byte[] buffer = new byte[size];
	String threadID;
	String request;
	String response;
	Tools tools = new Tools();
	InputStream in;
	OutputStream out;

	public MovieRetailer()
	{
		try
		{
			socket = new Socket(host, port, false);
			socket.setSoTimeout(3000);
			in = socket.getInputStream();
			out = socket.getOutputStream();
		} catch (IOException e)
		{
			System.out.println(e);
		}
	}

	public boolean isConnectionAvailable(String threadID)
	{
		this.threadID = threadID;
		request = threadID + "|isConnectionAvailable|" + threadID + "|";
		boolean isConnectionAvailable = false;
		System.out.println(threadID + ": online requesting ....");
		do
		{
			System.out.println("online request = " + request);
			response = this.sendRequest(request);
			System.out.println(threadID + ": (online request result): "
					+ response);
			try
			{
				Thread.sleep(1000);
			} catch (Exception e)
			{
				System.out.println(e);
			}
			isConnectionAvailable = tools.convertStringToBoolean(response);
		} while (!isConnectionAvailable);
		System.out.println(threadID + ": online requesting done!");
		return isConnectionAvailable;
	}

	public boolean signInServer(String threadID)
	{
		System.out.println(threadID + ": signInServer starting ...");
		this.threadID = threadID;
		request = threadID + "|signInServer|" + threadID + "|";
		response = this.sendRequest(request);
		System.out.println(threadID + ": signInServer response = " + response);
		return tools.convertStringToBoolean(response);
	}

	public boolean signOutServer(String threadID)
	{
		request = threadID + "|signOutServer|" + threadID + "|";
		response = this.sendRequest(request);
		return tools.convertStringToBoolean(response);
	}

	public boolean signUp(String newAccountName, String newAccountPassword,
			String newCreditCardNumber)
	{
		request = threadID + "|signUp|" + newAccountName + "#"
				+ newAccountPassword + "#" + newCreditCardNumber + "#|";
		response = this.sendRequest(request);
		Hashtable responseHash = tools.convertStringToHash(response);
		return tools.convertStringToBoolean((String) responseHash.get("0"));
	}

	public boolean signIn(String inputAccountName, String inputAccountPassword)
	{
		request = threadID + "|signIn|" + inputAccountName + "#"
				+ inputAccountPassword + "#|";
		response = this.sendRequest(request);
		Hashtable responseHash = tools.convertStringToHash(response);
		return tools.convertStringToBoolean((String) responseHash.get("0"));
	}

	public float getPrice(String bookName)
	{
		request = threadID + "|getPrice|" + bookName + "#|";
		System.out.println("threadID (MovieRetailer Proxy) = " + threadID);
		response = this.sendRequest(request);
		Hashtable responseHash = tools.convertStringToHash(response);
		if (response != null)
		{
			return tools.convertStringToFloat((String) responseHash.get("0"));
		} else
		{
			return tools.convertStringToFloat("0");
		}
	}

	public String getPublisher(String bookName)
	{
		request = threadID + "|getPublisher|" + bookName + "#|";
		response = this.sendRequest(request);
		Hashtable responseHash = tools.convertStringToHash(response);
		return (String) responseHash.get("0");
	}

	public String getAuthor(String bookName)
	{
		request = threadID + "|getAuthor|" + bookName + "#|";
		response = this.sendRequest(request);
		Hashtable responseHash = tools.convertStringToHash(response);
		return (String) responseHash.get("0");
	}

	public boolean putIntoShoppingCart(String bookName, int quantity)
	{
		request = threadID + "|putIntoShoppingCart|" + bookName + "#"
				+ tools.convertIntToString(quantity) + "#|";
		response = this.sendRequest(request);
		Hashtable responseHash = tools.convertStringToHash(response);
		return tools.convertStringToBoolean((String) responseHash.get("0"));
	}

	public boolean moveOutShoppingCart(String bookName, int quantity)
	{
		request = threadID + "|moveOutShoppingCart|" + bookName + "#"
				+ tools.convertIntToString(quantity) + "#|";
		response = this.sendRequest(request);
		Hashtable responseHash = tools.convertStringToHash(response);
		return tools.convertStringToBoolean((String) responseHash.get("0"));
	}

	public Hashtable getShoppingCart()
	{
		request = threadID + "|getShoppingCart|" + "#|";
		response = this.sendRequest(request);
		return tools.convertStringToHash(response);
	}

	public Hashtable buyMovies(String inputCreditCardNumber)
	{
		request = threadID + "|buyMovies|" + inputCreditCardNumber + "#|";
		response = this.sendRequest(request);
		Hashtable responseHash = tools.convertStringToHash(response);
		return responseHash;
	}

	private String sendRequest(String request)
	{
		String response = null;
		int length = 0;
		try
		{
			do
			{
				buffer = request.getBytes();

				out.write(buffer, 0, request.length());
				out.flush();

				buffer = new byte[size];

				length = in.read(buffer);
				// System.out.println("length (sendRequest) = " + length);
				response = new String(buffer);
				try
				{
					Thread.sleep(1000);
				} catch (Exception e)
				{
					System.out.println(e);
				}
				int index = response.lastIndexOf("#");
				response = response.substring(0, index);
			} while (length == 0);
			// System.out.println("response (sendRequest) = " + response);
			// System.out.println("response (in sendRequest) = " + response);
			return response;
		} catch (IOException e)
		{
			return null;
		}
	}
}
