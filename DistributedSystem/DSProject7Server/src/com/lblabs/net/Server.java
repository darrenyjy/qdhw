package com.lblabs.net;

import java.io.*;
import java.net.*;
import java.util.Hashtable;
//import java.util.ArrayList;
import java.util.HashSet;

import com.lblabs.dns.RegistryServer;
import com.lblabs.web.WebServer;

import com.lblabs.business.BookRetailer;
import com.lblabs.tools.Tools;
import com.lblabs.tools.Queue;

import com.lblabs.sync.Mutexer;
import com.lblabs.sync.Locker;

public class Server
{
	static final int size = 60000;

	public static void main(String argv[])
	{
		Hashtable requestQueueHash = new Hashtable();
		Queue generalRequestQueue = new Queue();
		Queue generalResponseQueue = new Queue();
		String threadID;
//		ArrayList threadIDList = new ArrayList();
		HashSet threadIDSet = new HashSet();

		Locker locker = new Locker();
		Mutexer mutexer = new Mutexer();

		ConnectionStatus connectionStatus = new ConnectionStatus();

		Communicator communicator = new Communicator(requestQueueHash, generalRequestQueue, generalResponseQueue);
		communicator.start();

		RequestMessage requestMessage = new RequestMessage();
		ResponseMessage responseMessage = new ResponseMessage();
		System.out.println("Waiting for requests ...");

		while (true)
		{
			try
			{
				Thread.sleep(1);
			}
			catch (Exception e)
			{
				System.out.println(e);
			}

			if (!generalRequestQueue.isEmpty())
			{
				requestMessage = (RequestMessage)generalRequestQueue.dequeue();
//System.out.println("generalRequestQueue.count() (in main) = " + generalRequestQueue.count());
//System.out.println("generalResponseQueue.count() (in main) = " + generalResponseQueue.count());
//System.out.println("requestQueueHash.size() (in main) = " + requestQueueHash.size());
//System.out.println("requestMessage.threadID (in main) = " + requestMessage.threadID);
//System.out.println("requestMessage.methodName (in main) = " + requestMessage.methodName);
//System.out.println("requestMessage.parameterString (in main) = " + requestMessage.parameterString);
//System.out.println("threadIDSet = " + threadIDSet);
//				if (!threadIDList.contains(requestMessage.threadID) && requestMessage.methodName.equals("isConnectionAvailable"))
				if (requestMessage.methodName.equals("isConnectionAvailable"))
				{
//System.out.println("connectionStatus.getRegularConnection() = " + connectionStatus.getRegularConnection());
					if (connectionStatus.getRegularConnection())
					{
						threadIDSet.add(requestMessage.threadID);
						Queue requestQueue = new Queue();
						requestQueueHash.put(requestMessage.threadID, requestQueue);
						BusinessServerThread businessServerThread = new BusinessServerThread(mutexer, locker, requestMessage.threadID, threadIDSet, requestQueueHash, requestQueue, generalResponseQueue, connectionStatus);
						businessServerThread.start();
						responseMessage.threadID = requestMessage.threadID;
						responseMessage.returnValue = "true";
						responseMessage.address = requestMessage.address;
						responseMessage.port = requestMessage.port;
						generalResponseQueue.enqueue(responseMessage);
//System.out.println("generalResponseQueue.isEmpty() (in main) = " + generalResponseQueue.isEmpty());
					}
					else
					{
//System.out.println("No more connections are available!");
						responseMessage.threadID = requestMessage.threadID;
						responseMessage.address = requestMessage.address;
						responseMessage.port = requestMessage.port;
						responseMessage.returnValue = "false";
						generalResponseQueue.enqueue(responseMessage);
					}
				}
			}
		}
	}
}

class Communicator extends Thread
{
	DatagramSocket socket = null;
	DatagramPacket packet = null;
	Hashtable requestQueueHash;
	Queue generalRequestQueue;
	Queue generalResponseQueue;
	Responser responser;
	public Communicator(Hashtable requestQueueHash, Queue generalRequestQueue, Queue generalResponseQueue)
	{
		this.requestQueueHash = requestQueueHash;
		this.generalRequestQueue = generalRequestQueue;
		this.generalResponseQueue = generalResponseQueue;
		try
		{
			socket = new DatagramSocket(8888);			
		}
		catch (SocketException e)
		{
			// Something went wrong with the socket connection
			System.out.println(e.toString());
		}
		catch (IOException e)
		{
			// Something went wrong on receive
			System.out.println(e.toString());
		}
		responser = new Responser(socket, generalResponseQueue);
		responser.start();
	}

	public void run()
	{
		try
		{
			int size = 60000;
			String request = null;
			String response = null;

			int index;
			String threadID;
			String methodName;
			String parameterString;
			Hashtable parameterHash = new Hashtable();
			Hashtable responseHash = new Hashtable();

			while (true)
			{
				// create a datagram packet to receive message in
				byte[] reqBuffer = new byte[size];
				byte[] resBuffer = new byte[size];
				packet = new DatagramPacket(reqBuffer, size);

				// wait for message from client
				socket.receive(packet);

				// extract information from datagram packet
				reqBuffer = packet.getData();
				int port = packet.getPort();
				int length = packet.getLength();
				InetAddress address = packet.getAddress();

				// print information to standard output
				request = new String(reqBuffer);
				if (request != null)
				{
					index = request.indexOf("|");
					threadID = request.substring(0, index);
//System.out.println("threadID (in commuicator) = " + threadID);
					request = request.substring(index + 1);
					index = request.indexOf("|");
					methodName = request.substring(0, index);
//System.out.println("methodName (in communicator) = " + methodName);
					request = request.substring(index + 1);
					index = request.indexOf("|");
					parameterString = request.substring(0, index);
//System.out.println("parameterString (in communicator) = " + parameterString);
					RequestMessage requestMessage = new RequestMessage();
					requestMessage.threadID = threadID;
//System.out.println("requestMessage.threadID (in Communicator) = " + requestMessage.threadID);
					requestMessage.methodName = methodName;
					requestMessage.parameterString = parameterString;
					requestMessage.address = address;
					requestMessage.port = port;
					if (requestQueueHash.size() > 0)
					{
						Queue requestQueue = (Queue)requestQueueHash.get(threadID);
						if (requestQueue != null)
						{
							requestQueue.enqueue(requestMessage);
							requestQueueHash.put(threadID, requestQueue);
						}
					}
					generalRequestQueue.enqueue(requestMessage);
//System.out.println("generalRequestQueue.count() (in Communicator) = " + generalRequestQueue.count());
				}
			}
		}
		catch (SocketException e)
		{
			// Something went wrong with the socket connection
			System.out.println(e.toString());
		}
		catch (IOException e)
		{
			// Something went wrong on receive
			System.out.println(e.toString());
		}
	}
}

class Responser extends Thread
{
	DatagramSocket socket;
	DatagramPacket packet;
	int size = 1024;
	Queue generalResponseQueue;
	public Responser(DatagramSocket socket, Queue generalResponseQueue)
	{
		this.socket = socket;
		this.generalResponseQueue = generalResponseQueue;
	}

	public void run()
	{
		while (true)
		{
			try
			{
				Thread.sleep(1);						
			}
			catch (Exception e)
			{
				System.out.println(e);
			}
			if (!generalResponseQueue.isEmpty())
			{
				byte[] resBuffer = new byte[size];
				ResponseMessage responseMessage = new ResponseMessage();
				responseMessage = (ResponseMessage)generalResponseQueue.dequeue();

				// send the message back to the client
				if (responseMessage.returnValue == null)
				{
					responseMessage.returnValue = "null#";
				}
				else
				{
//System.out.println("responseMessage.returnValue = " + responseMessage.returnValue);
					responseMessage.returnValue += "#";
				}
				resBuffer = responseMessage.returnValue.getBytes();
				try
				{
					packet = new DatagramPacket(resBuffer, resBuffer.length, responseMessage.address, responseMessage.port);
					socket.send(packet);
				}
				catch (SocketException e)
				{
					// Something went wrong with the socket connection
					System.out.println(e.toString());
				}
				catch (IOException e)
				{
					// Something went wrong on receive
					System.out.println(e.toString());
				}
			}
		}
	}
}

class BusinessServerThread extends Thread
{
	String threadID;
	HashSet threadIDSet;
	ConnectionStatus connectionStatus;
	Hashtable requestQueueHash;
	Queue requestQueue;
	Queue generalResponseQueue;
	BusinessObject businessObject;
//	BookRetailer bookRetailer = new BookRetailer();
	RegistryServer registryServer = new RegistryServer();
	WebServer webServer = new WebServer();
	RequestMessage requestMessage = new RequestMessage();
	ResponseMessage responseMessage = new ResponseMessage();

	Mutexer mutexer;
	Locker locker;

	public BusinessServerThread(Mutexer mutexer, Locker locker, String threadID, HashSet threadIDSet, Hashtable requestQueueHash, Queue requestQueue, Queue generalResponseQueue, ConnectionStatus connectionStatus)
	{
//System.out.println("New BusinessServerThread for " + threadID);
		this.mutexer = mutexer;
		this.locker = locker;
		this.threadID = threadID;
		this.threadIDSet = threadIDSet;
		this.requestQueueHash = requestQueueHash;
		this.requestQueue = requestQueue;
		this.generalResponseQueue = generalResponseQueue;
		this.connectionStatus = connectionStatus;
		this.businessObject = new BusinessObject(this.threadID, this.mutexer, this.locker);
	}

	public void run()
	{
		int index;
		Tools tools = new Tools();
		boolean isProcessed = false;
		while (true)
		{
			try
			{
				Thread.sleep(1);
			}
			catch (Exception e)
			{
				System.out.println(e);
			}
			if (!requestQueue.isEmpty())
			{
//System.out.println("requestQueue.count (in businessServerThread) = " + requestQueue.count());
				requestMessage = (RequestMessage)requestQueue.peek();
				isProcessed = false;
				if ((requestMessage.threadID).equals(threadID))
				{
					if ((requestMessage.methodName).equals("signInServer"))
					{
						responseMessage.returnValue = "true";
						requestQueue.dequeue();
						isProcessed = true;
					}
					else if ((requestMessage.methodName).equals("signOutServer"))
					{
						responseMessage.returnValue = "true";
						threadIDSet.remove(threadID);
System.out.println("threadIDSet (in businessServerThread) = " + threadIDSet);
						connectionStatus.getRegularDisconnection();
						responseMessage.threadID = threadID;
						responseMessage.address = requestMessage.address;
						responseMessage.port = requestMessage.port;
//						requestQueue.dequeue();
						requestQueue.clear();
						requestQueueHash.remove(threadID);
System.out.println("requestQueueHash.size() (in businessServerThread) = " + requestQueueHash.size());
						isProcessed = true;
						generalResponseQueue.enqueue(responseMessage);
						break;
					}
					else if ((requestMessage.methodName).equals("regobj"))
					{

					}
					else if ((requestMessage.methodName).equals("register"))
					{
						Hashtable parameterHash = tools.convertStringToHash(requestMessage.parameterString);
						String yourURL = (String)parameterHash.get("0");
						String yourAddress = (String)parameterHash.get("1");
						boolean isRegistrySuccessful = registryServer.registerDNS(yourURL, yourAddress);
						responseMessage.threadID = threadID;
						responseMessage.returnValue = tools.convertBooleanToString(isRegistrySuccessful);
//						generalResponseQueue.enqueue(responseMessage);
						isProcessed = true;
					}
					else if ((requestMessage.methodName).equals("searchAddress"))
					{
						String domainName = requestMessage.parameterString;
System.out.println("domainName = " + domainName);
						String address = registryServer.searchAddress(domainName);
System.out.println("address = " + address);
						responseMessage.threadID = threadID;
						responseMessage.returnValue = address;
//						generalResponseQueue.enqueue(responseMessage);
						isProcessed = true;
					}
					else if ((requestMessage.methodName).equals("getPage"))
					{
						String pageName = requestMessage.parameterString;
System.out.println("pageName = " + pageName);
						StringBuffer pageBuffer = webServer.getPage(pageName);
						responseMessage.threadID = threadID;
						responseMessage.returnValue = pageBuffer.toString();
//						generalResponseQueue.enqueue(responseMessage);
						isProcessed = true;
					}
					else
					{
//System.out.println("requestMessage.methodName = " + requestMessage.methodName);
//System.out.println("requestMessage.parameterString = " + requestMessage.parameterString);
						Hashtable responseHash = businessObject.invoke(requestMessage.methodName, requestMessage.parameterString);
//System.out.println("responseHash (in server) = " + responseHash);
						if (responseHash != null)
						{
							responseMessage.returnValue = tools.convertHashToString(responseHash);
							requestQueue.dequeue();
							isProcessed = true;
						}
					}
					if (isProcessed)
					{
						responseMessage.threadID = threadID;
						responseMessage.address = requestMessage.address;
						responseMessage.port = requestMessage.port;
						generalResponseQueue.enqueue(responseMessage);
					}
				}
			}
		}
	}
}

class RequestMessage
{
	public String threadID;
	public String methodName;
	public String parameterString;
	public InetAddress address;
	public int port;
}

class ResponseMessage
{
	public String threadID;
	public String returnValue;
	public InetAddress address;
	public int port;
}

class ConnectionStatus
{
	int connectionLimitation = 1000;
	int criticalConnectionLimitation = 1;
	int currentConnection = 0;
	int currentCriticalConnection = 0;

	public boolean getRegularConnection()
	{
//System.out.println("currentConnection = " + currentConnection);
//System.out.println("connectionLimitation = " + connectionLimitation);
		if (currentConnection < connectionLimitation)
		{
			currentConnection ++;
			return true;
		}
		else
		{
			return false;
		}
	}

	public boolean getCriticalConnection()
	{
		if (currentCriticalConnection < criticalConnectionLimitation)
		{
			currentCriticalConnection ++;
			return true;
		}
		else
		{
			return false;
		}
	}

	public void getRegularDisconnection()
	{
		currentConnection --;
	}

	public void getCriticalDisconnection()
	{
		currentCriticalConnection --;
	}
}