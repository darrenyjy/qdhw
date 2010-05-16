package com.lblabs.net;

import java.io.IOException;
import java.net.DatagramPacket;
import java.net.DatagramSocket;
import java.net.InetAddress;
import java.net.SocketException;
import java.util.ArrayList;
import java.util.Hashtable;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;

import com.lblabs.business.BookRetailer;
import com.lblabs.tools.Queue;
import com.lblabs.tools.Tools;

public class Server
{
	static final int size = 1024;
	private static Log log = LogFactory.getLog(Server.class);

	public static void main(String argv[])
	{
		Hashtable requestQueueHash = new Hashtable();
		Queue generalRequestQueue = new Queue();
		Queue generalResponseQueue = new Queue();
		String threadID;
		ArrayList threadIDList = new ArrayList();

		ConnectionStatus connectionStatus = new ConnectionStatus();

		Communicator communicator = new Communicator(requestQueueHash,
				generalRequestQueue, generalResponseQueue);
		communicator.start();

		RequestMessage requestMessage = new RequestMessage();
		ResponseMessage responseMessage = new ResponseMessage();

		while (true)
		{
			try
			{
				Thread.sleep(100);
			} catch (Exception e)
			{
				log.error(e);
			}

			if (!generalRequestQueue.isEmpty())
			{
				requestMessage = (RequestMessage) generalRequestQueue.dequeue();
				// System.out.println("generalRequestQueue.count() (in main) = "
				// + generalRequestQueue.count());
				// System.out.println("requestMessage.threadID (in main) = " +
				// requestMessage.threadID);
				// System.out.println("requestMessage.methodName (in main) = " +
				// requestMessage.methodName);
				// System.out.println("requestMessage.parameterString (in main) = "
				// + requestMessage.parameterString);
				if (!threadIDList.contains(requestMessage.threadID)
						&& requestMessage.methodName
								.equals("isConnectionAvailable"))
				{
					// System.out.println("connectionStatus.getRegularConnection() = "
					// + connectionStatus.getRegularConnection());

					if (connectionStatus.getRegularConnection())
					{
						threadIDList.add(requestMessage.threadID);
						Queue requestQueue = new Queue();
						requestQueueHash.put(requestMessage.threadID,
								requestQueue);
						BusinessServerThread businessServerThread = new BusinessServerThread(
								requestMessage.threadID, threadIDList,
								requestQueue, generalResponseQueue,
								connectionStatus);
						businessServerThread.start();
						responseMessage.threadID = requestMessage.threadID;
						responseMessage.returnValue = "true";
						responseMessage.address = requestMessage.address;
						responseMessage.port = requestMessage.port;
						generalResponseQueue.enqueue(responseMessage);
						// System.out.println("generalResponseQueue.isEmpty() (in main) = "
						// + generalResponseQueue.isEmpty());
					} else
					{
						// System.out.println("No more connections are available!");
						responseMessage.threadID = requestMessage.threadID;
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

	private static Log log = LogFactory.getLog(Communicator.class);

	public Communicator(Hashtable requestQueueHash, Queue generalRequestQueue,
			Queue generalResponseQueue)
	{
		this.requestQueueHash = requestQueueHash;
		this.generalRequestQueue = generalRequestQueue;
		this.generalResponseQueue = generalResponseQueue;
		try
		{
			socket = new DatagramSocket(8888);
		} catch (SocketException e)
		{
			// Something went wrong with the socket connection
			log.error(e.toString());
		} catch (IOException e)
		{
			// Something went wrong on receive
			log.error(e.toString());
		}
		responser = new Responser(socket, generalResponseQueue);
		responser.start();
	}

	public void run()
	{
		try
		{
			int size = 1024;
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
				log.debug("Waiting for requests ...");
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
					log.debug("threadID (in commuicator) = " + threadID);
					request = request.substring(index + 1);
					index = request.indexOf("|");
					methodName = request.substring(0, index);
					log.debug("methodName (in communicator) = " + methodName);
					request = request.substring(index + 1);
					index = request.indexOf("|");
					parameterString = request.substring(0, index);

					RequestMessage requestMessage = new RequestMessage();
					requestMessage.threadID = threadID;
					// System.out.println("requestMessage.threadID (in Communicator) = "
					// + requestMessage.threadID);
					requestMessage.methodName = methodName;
					requestMessage.parameterString = parameterString;
					requestMessage.address = address;
					requestMessage.port = port;
					if (requestQueueHash.size() > 0)
					{
						Queue requestQueue = (Queue) requestQueueHash
								.get(threadID);
						if (requestQueue != null)
						{
							requestQueue.enqueue(requestMessage);
							requestQueueHash.put(threadID, requestQueue);
						}
					}
					generalRequestQueue.enqueue(requestMessage);
					System.out
							.println("generalRequestQueue.count() (in Communicator) = "
									+ generalRequestQueue.count());
				}
			}
		} catch (SocketException e)
		{
			// Something went wrong with the socket connection
			log.error(e.toString());
		} catch (IOException e)
		{
			// Something went wrong on receive
			log.error(e.toString());
		}
	}
}

class Responser extends Thread
{
	DatagramSocket socket;
	DatagramPacket packet;
	int size = 1024;
	Queue generalResponseQueue;
	private static Log log = LogFactory.getLog(Responser.class);

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
			} catch (Exception e)
			{
				log.debug(e);
			}
			if (!generalResponseQueue.isEmpty())
			{
				byte[] resBuffer = new byte[size];
				ResponseMessage responseMessage = new ResponseMessage();
				responseMessage = (ResponseMessage) generalResponseQueue
						.dequeue();

				// send the message back to the client
				if (responseMessage.returnValue == null)
				{
					responseMessage.returnValue = "null#";
				} else
				{
					responseMessage.returnValue += "#";
				}
				resBuffer = responseMessage.returnValue.getBytes();
				try
				{
					packet = new DatagramPacket(resBuffer, resBuffer.length,
							responseMessage.address, responseMessage.port);
					socket.send(packet);
				} catch (SocketException e)
				{
					// Something went wrong with the socket connection
					log.error(e.toString());
				} catch (IOException e)
				{
					// Something went wrong on receive
					log.error(e.toString());
				}
			}
		}
	}
}

class BusinessServerThread extends Thread
{
	String threadID;
	ArrayList threadIDList;
	ConnectionStatus connectionStatus;
	Queue requestQueue;
	Queue generalResponseQueue;
	BookRetailer bookRetailer = new BookRetailer();
	RequestMessage requestMessage = new RequestMessage();
	ResponseMessage responseMessage = new ResponseMessage();
	private static Log log = LogFactory.getLog(BusinessServerThread.class);

	public BusinessServerThread(String threadID, ArrayList threadIDList,
			Queue requestQueue, Queue generalResponseQueue,
			ConnectionStatus connectionStatus)
	{
		log.debug("New BusinessServerThread for " + threadID);
		this.threadID = threadID;
		this.threadIDList = threadIDList;
		this.requestQueue = requestQueue;
		this.generalResponseQueue = generalResponseQueue;
		this.connectionStatus = connectionStatus;
	}

	public void run()
	{
		int index;
		Tools tools = new Tools();
		while (true)
		{
			try
			{
				Thread.sleep(1);
			} catch (Exception e)
			{
				log.debug(e);
			}
			if (!requestQueue.isEmpty())
			{
				System.out
						.println("requestQueue.count (in businessServerThread) = "
								+ requestQueue.count());
				requestMessage = (RequestMessage) requestQueue.dequeue();
				// System.out.println("threadID (in businessServerThread) = " +
				// threadID);
				// System.out.println("requestMessage.threadID = " +
				// requestMessage.threadID);
				if ((requestMessage.threadID).equals(threadID))
				{
					// System.out.println(threadID +
					// ": requestMessage.methodName (in BusinessServerThread) = "
					// + requestMessage.methodName);
					if ((requestMessage.methodName).equals("signIn"))
					{
						responseMessage.returnValue = "true";
					} else if ((requestMessage.methodName).equals("signOut"))
					{
						responseMessage.returnValue = "true";
						threadIDList.remove(threadID);
						connectionStatus.getRegularDisconnection();
						responseMessage.threadID = threadID;
						responseMessage.address = requestMessage.address;
						responseMessage.port = requestMessage.port;
						generalResponseQueue.enqueue(responseMessage);
						break;
					} else if ((requestMessage.methodName).equals("getPrice"))
					{
						String bookName = requestMessage.parameterString;
						float bookPrice = bookRetailer.getPrice(bookName);
						// System.out.println("bookPrice = " + bookPrice);
						responseMessage.returnValue = tools
								.convertFloatToString(bookPrice);
					} else if ((requestMessage.methodName)
							.equals("getPublisher"))
					{
						String bookName = requestMessage.parameterString;
						String publisher = bookRetailer.getPublisher(bookName);
						// System.out.println("publisher = " + publisher);
						responseMessage.returnValue = publisher;
					} else if ((requestMessage.methodName).equals("getAuthor"))
					{
						String bookName = requestMessage.parameterString;
						String author = bookRetailer.getAuthor(bookName);
						// System.out.println("author = " + author);
						responseMessage.returnValue = author;
					} else if ((requestMessage.methodName).equals("buyBooks"))
					{
						// System.out.println("parameterString (in BusinessServerThread) = "
						// + requestMessage.parameterString);
						Hashtable parameterHash = tools
								.convertStringToHash(requestMessage.parameterString);
						String inputAccountName = (String) parameterHash
								.get("0");
						String inputAccountPassword = (String) parameterHash
								.get("1");
						String inputCreditCardNumber = (String) parameterHash
								.get("2");
						String inputBookName = (String) parameterHash.get("3");
						int inputQuantity = tools
								.convertStringToInt((String) parameterHash
										.get("4"));
						Hashtable responseHash = bookRetailer.buyBooks(
								inputAccountName, inputAccountPassword,
								inputCreditCardNumber, inputBookName,
								inputQuantity);
						responseMessage.returnValue = tools
								.convertHashToString(responseHash);
					}
					responseMessage.threadID = threadID;
					responseMessage.address = requestMessage.address;
					responseMessage.port = requestMessage.port;
					generalResponseQueue.enqueue(responseMessage);
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
	private static Log log = LogFactory.getLog(ConnectionStatus.class);

	public boolean getRegularConnection()
	{
		System.out.println("currentConnection = " + currentConnection);
		System.out.println("connectionLimitation = " + connectionLimitation);
		if (currentConnection < connectionLimitation)
		{
			currentConnection++;
			return true;
		} else
		{
			return false;
		}
	}

	public boolean getCriticalConnection()
	{
		if (currentCriticalConnection < criticalConnectionLimitation)
		{
			currentCriticalConnection++;
			return true;
		} else
		{
			return false;
		}
	}

	public void getRegularDisconnection()
	{
		currentConnection--;
	}

	public void getCriticalDisconnection()
	{
		currentCriticalConnection--;
	}
}