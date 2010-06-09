package com.lblabs.business;


public class Client
{
	public static void main(String args[])
	{
		for (int i = 0; i < 10; i++)
		{
			BusinessClientThread businessClientThread = new BusinessClientThread(
					"T" + i, "0#1#3#4#5#6#2#");
			businessClientThread.start();
		}
	}
}