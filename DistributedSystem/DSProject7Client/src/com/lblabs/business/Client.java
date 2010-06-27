package com.lblabs.business;

import java.io.*;
import java.util.*;

//import com.lblabs.net.BookRetailer;

public class Client
{
	public static void main(String args[])
	{
		TransactionT transactionT = new TransactionT("TransactionT");
		TransactionU transactionU = new TransactionU("TransactionU");
		transactionT.start();
		transactionU.start();

		/*
		for (int i = 0; i < 2; i ++)
		{
			BusinessClientThread businessClientThread = new BusinessClientThread("T" + i);
			businessClientThread.start();
		}
		*/
	}
}