package com.lblabs.business;

import java.io.*;
import java.util.*;

import com.lblabs.net.BookRetailer;

public class Client
{
	public static void main(String args[])
	{
		for (int i = 0; i < 15; i ++)
		{
			BusinessClientThread businessClientThread = new BusinessClientThread("T" + i, "0#1#3#4#5#6#2#");
			businessClientThread.start();
		}
	}
}