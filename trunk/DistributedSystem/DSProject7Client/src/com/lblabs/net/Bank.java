package com.lblabs.net;

import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;
import java.net.Socket;
import java.util.Hashtable;

import com.lblabs.tools.Tools;

public class Bank
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

	public Bank()
	{
		try
		{
			socket = new Socket(host, port, false);
			socket.setSoTimeout(5000);
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

	public float getAccountBalance(String accountName)
	{
		request = threadID + "|getAccountBalance|" + accountName + "#|";
		response = this.sendRequest(request);
		Hashtable responseHash = tools.convertStringToHash(response);
		if (response != null)
		{
			return tools.convertStringToFloat((String) responseHash.get("0"));
		} else
		{
			return 0;
		}
	}

	public float getTotalDeposit()
	{
		request = threadID + "|getTotalDeposit|" + "#|";
		response = this.sendRequest(request);
		Hashtable responseHash = tools.convertStringToHash(response);
		if (response != null)
		{
			return tools.convertStringToFloat((String) responseHash.get("0"));
		} else
		{
			return 0;
		}
	}

	public float deposit(String accountName, float accountBalance,
			float depositAmount)
	{
		request = threadID + "|deposit|" + accountName + "#"
				+ tools.convertFloatToString(accountBalance) + "#"
				+ tools.convertFloatToString(depositAmount) + "#|";
		response = this.sendRequest(request);
		Hashtable responseHash = tools.convertStringToHash(response);
		if (response != null)
		{
			return tools.convertStringToFloat((String) responseHash.get("0"));
		} else
		{
			return 0;
		}
	}

	public float withdraw(String accountName, float accountBalance,
			float withdrawAmount)
	{
		request = threadID + "|withdraw|" + accountName + "#"
				+ tools.convertFloatToString(accountBalance) + "#"
				+ tools.convertFloatToString(withdrawAmount) + "#|";
		response = this.sendRequest(request);
		Hashtable responseHash = tools.convertStringToHash(response);
		if (response != null)
		{
			return tools.convertStringToFloat((String) responseHash.get("0"));
		} else
		{
			return 0;
		}
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
					Thread.sleep(5000);
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
