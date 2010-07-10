package com.lblabs.business;

import java.io.*;
import java.util.*;

import com.lblabs.net.BookRetailer;

public class Client
{
	public static void main(String args[])
	{
		for (int i = 0; i < 10; i ++)
		{
			BusinessClientThread businessClientThread = new BusinessClientThread("T" + i);
			businessClientThread.start();
		}
	}
}