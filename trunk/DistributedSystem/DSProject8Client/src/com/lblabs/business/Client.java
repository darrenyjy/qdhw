package com.lblabs.business;

public class Client
{
	public static void main(String args[])
	{
		for (int i = 0; i < 1; i++)
		{
			MovieBusinessClientThread businessClientThread = new MovieBusinessClientThread(
					"T" + i);
			businessClientThread.start();
		}
	}
}