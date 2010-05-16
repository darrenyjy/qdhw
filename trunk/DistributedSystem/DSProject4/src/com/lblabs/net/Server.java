package com.lblabs.net;

import java.io.FileInputStream;
import java.io.FileWriter;
import java.io.IOException;
import java.io.InputStream;
import java.io.PrintWriter;
import java.net.DatagramPacket;
import java.net.DatagramSocket;
import java.net.InetAddress;
import java.net.SocketException;
import java.util.ArrayList;
import java.util.Enumeration;
import java.util.Hashtable;

import org.w3c.dom.Document;
import org.w3c.dom.Element;

import com.ibm.xml.parser.Parser;
import com.ibm.xml.parser.TXDocument;
import com.lblabs.business.BookRetailer;
import com.lblabs.dns.RegistryServer;
import com.lblabs.tools.Queue;
import com.lblabs.tools.Tools;
import com.lblabs.web.WebServer;

public class Server
{
	static final int size = 60000;

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
				Thread.sleep(1);
			} catch (Exception e)
			{
				System.out.println(e);
			}

			if (!generalRequestQueue.isEmpty())
			{
				requestMessage = (RequestMessage) generalRequestQueue.dequeue();
				if (!threadIDList.contains(requestMessage.threadID)
						&& requestMessage.methodName
								.equals("isConnectionAvailable"))
				{
					// requestMessage =
					// (RequestMessage)generalRequestQueue.dequeue();
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
						generalResponseQueue.enqueue(responseMessage);
					} else
					{
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
	Hashtable requestQueueHash;
	Queue generalRequestQueue;
	Queue generalResponseQueue;
	static final int size = 60000;

	public Communicator(Hashtable requestQueueHash, Queue generalRequestQueue,
			Queue generalResponseQueue)
	{
		this.requestQueueHash = requestQueueHash;
		this.generalRequestQueue = generalRequestQueue;
		this.generalResponseQueue = generalResponseQueue;
	}

	public void run()
	{
		try
		{
			DatagramSocket socket = null;
			DatagramPacket packet = null;
			String request = null;
			String response = null;

			socket = new DatagramSocket(8888);
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
				System.out.println("Waiting for requests ...");
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
					System.out.println("threadID = " + threadID);
					request = request.substring(index + 1);
					index = request.indexOf("|");
					methodName = request.substring(0, index);
					System.out.println("methodName = " + methodName);
					request = request.substring(index + 1);
					index = request.indexOf("|");
					parameterString = request.substring(0, index);
					System.out.println("parameterString = " + parameterString);

					RequestMessage requestMessage = new RequestMessage();
					requestMessage.threadID = threadID;
					requestMessage.methodName = methodName;
					requestMessage.parameterString = parameterString;
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
				}

				while (true)
				{
					try
					{
						Thread.sleep(1);
					} catch (Exception e)
					{
						System.out.println(e);
					}
					if (!generalResponseQueue.isEmpty())
					{
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
						length = resBuffer.length;
						packet = new DatagramPacket(resBuffer, length, address,
								port);
						socket.send(packet);
						break;
					}
				}
			}
		} catch (SocketException e)
		{
			// Something went wrong with the socket connection
			System.out.println(e.toString());
		} catch (IOException e)
		{
			// Something went wrong on receive
			System.out.println(e.toString());
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
	RegistryServer registryServer = new RegistryServer();
	WebServer webServer = new WebServer();
	RequestMessage requestMessage = new RequestMessage();
	ResponseMessage responseMessage = new ResponseMessage();

	public BusinessServerThread(String threadID, ArrayList threadIDList,
			Queue requestQueue, Queue generalResponseQueue,
			ConnectionStatus connectionStatus)
	{
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
				System.out.println(e);
			}
			if (!requestQueue.isEmpty())
			{
				requestMessage = (RequestMessage) requestQueue.dequeue();
				if ((requestMessage.threadID).equals(threadID))
				{
					if ((requestMessage.methodName).equals("signIn"))
					{
						responseMessage.threadID = threadID;
						responseMessage.returnValue = "true";
						generalResponseQueue.enqueue(responseMessage);
					} else if ((requestMessage.methodName).equals("signOut"))
					{
						responseMessage.threadID = threadID;
						responseMessage.returnValue = "true";
						threadIDList.remove(threadID);
						connectionStatus.getRegularDisconnection();
						generalResponseQueue.enqueue(responseMessage);
						break;
					} else if ((requestMessage.methodName).equals("getPrice"))
					{
						String bookName = requestMessage.parameterString;
						float bookPrice = bookRetailer.getPrice(bookName);
						responseMessage.threadID = threadID;
						responseMessage.returnValue = tools
								.convertFloatToString(bookPrice);
						generalResponseQueue.enqueue(responseMessage);
					} else if ((requestMessage.methodName)
							.equals("getPublisher"))
					{
						String bookName = requestMessage.parameterString;
						String publisher = bookRetailer.getPublisher(bookName);
						responseMessage.threadID = threadID;
						responseMessage.returnValue = publisher;
						generalResponseQueue.enqueue(responseMessage);
					} else if ((requestMessage.methodName).equals("getAuthor"))
					{
						String bookName = requestMessage.parameterString;
						String author = bookRetailer.getAuthor(bookName);
						responseMessage.threadID = threadID;
						responseMessage.returnValue = author;
						generalResponseQueue.enqueue(responseMessage);
					} else if ((requestMessage.methodName).equals("buyBooks"))
					{
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
						responseMessage.threadID = threadID;
						// responseMessage.returnValue =
						// tools.convertHashToString(responseHash);
						responseMessage.returnValue = (this
								.createReceiptPage(responseHash)).toString();
						generalResponseQueue.enqueue(responseMessage);
					} else if ((requestMessage.methodName).equals("register"))
					{
						Hashtable parameterHash = tools
								.convertStringToHash(requestMessage.parameterString);
						String yourURL = (String) parameterHash.get("0");
						String yourAddress = (String) parameterHash.get("1");
						boolean isRegistrySuccessful = registryServer
								.registerDNS(yourURL, yourAddress);
						responseMessage.threadID = threadID;
						responseMessage.returnValue = tools
								.convertBooleanToString(isRegistrySuccessful);
						generalResponseQueue.enqueue(responseMessage);
					} else if ((requestMessage.methodName)
							.equals("searchAddress"))
					{
						String domainName = requestMessage.parameterString;
						System.out.println("domainName = " + domainName);
						String address = registryServer
								.searchAddress(domainName);
						System.out.println("address = " + address);
						responseMessage.threadID = threadID;
						responseMessage.returnValue = address;
						generalResponseQueue.enqueue(responseMessage);
					} else if ((requestMessage.methodName).equals("getPage"))
					{
						String pageName = requestMessage.parameterString;
						System.out.println("pageName = " + pageName);
						StringBuffer pageBuffer = webServer.getPage(pageName);
						responseMessage.threadID = threadID;
						responseMessage.returnValue = pageBuffer.toString();
						generalResponseQueue.enqueue(responseMessage);
					}
				}
			}
		}
	}

	private StringBuffer createReceiptPage(Hashtable responseHash)
	{
		String responseXML = webServer.getResponseXML();
		try
		{
			InputStream is = new FileInputStream(responseXML);
			Parser parser = new Parser(responseXML);

			Document doc = parser.readStream(is);
			Element root = doc.getDocumentElement();

			Element titleItem;
			Element paragraphItem;
			Element paragraphTitleItem;
			Element descriptionItem;
			Element linkItem;
			Element linkPresentationItem;
			Element linkPageItem;
			Element formItem;
			Element formTitleItem;
			Element formMethodItem;
			Element formElementItem;
			Element formFieldItem;
			Element formValueItem;

			titleItem = doc.createElement("Title");
			titleItem.appendChild(doc.createTextNode("Receipt"));
			root.appendChild(titleItem);

			paragraphItem = doc.createElement("Paragraph");
			root.appendChild(paragraphItem);

			paragraphTitleItem = doc.createElement("ParagraphTitle");
			paragraphTitleItem.appendChild(doc
					.createTextNode("Your order is processed!"));
			paragraphItem.appendChild(paragraphTitleItem);

			descriptionItem = doc.createElement("Description");
			descriptionItem.appendChild(doc
					.createTextNode("This is your receipt"));
			paragraphItem.appendChild(descriptionItem);

			linkItem = doc.createElement("Link");
			paragraphItem.appendChild(linkItem);

			linkPresentationItem = doc.createElement("LinkPresentation");
			linkPresentationItem.appendChild(doc.createTextNode("Receipt"));
			linkItem.appendChild(linkPresentationItem);

			linkPageItem = doc.createElement("LinkPage");
			linkPageItem.appendChild(doc
					.createTextNode("http://www.ibm.com/index.xml"));
			linkItem.appendChild(linkPageItem);

			formItem = doc.createElement("Form");
			paragraphItem.appendChild(formItem);

			formTitleItem = doc.createElement("FormTitle");
			formTitleItem.appendChild(doc.createTextNode("Receipt Form"));
			formItem.appendChild(formTitleItem);

			formMethodItem = doc.createElement("FormMethod");
			formMethodItem.appendChild(doc.createTextNode("null"));
			formItem.appendChild(formMethodItem);

			Enumeration enumeration = responseHash.keys();
			String fieldName;
			String fieldValue;
			while (enumeration.hasMoreElements())
			{
				fieldName = (String) enumeration.nextElement();
				fieldValue = (String) responseHash.get(fieldName);

				formElementItem = doc.createElement("FormElement");
				formItem.appendChild(formElementItem);

				formFieldItem = doc.createElement("FormField");
				formFieldItem.appendChild(doc.createTextNode(fieldName));
				formElementItem.appendChild(formFieldItem);

				formValueItem = doc.createElement("FormValue");
				formValueItem.appendChild(doc.createTextNode(fieldValue));
				formElementItem.appendChild(formValueItem);
			}
			FileWriter fw = new FileWriter(responseXML);
			PrintWriter pw = new PrintWriter(fw, true);

			((TXDocument) doc).printWithFormat(pw);
		} catch (Exception e)
		{
			e.printStackTrace();
		}

		StringBuffer stringBuffer = new StringBuffer();
		stringBuffer = webServer.getPage("response.xml");
		return stringBuffer;
	}
}

class RequestMessage
{
	public String threadID;
	public String methodName;
	public String parameterString;
}

class ResponseMessage
{
	public String threadID;
	public String returnValue;
}

class ConnectionStatus
{
	int connectionLimitation = 100;
	int criticalConnectionLimitation = 1;
	int currentConnection = 0;
	int currentCriticalConnection = 0;

	public boolean getRegularConnection()
	{
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