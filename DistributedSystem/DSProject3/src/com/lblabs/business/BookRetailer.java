package com.lblabs.business;

import java.io.FileInputStream;
import java.io.FileWriter;
import java.io.InputStream;
import java.io.PrintWriter;
import java.util.Hashtable;
import java.util.concurrent.locks.Lock;
import java.util.concurrent.locks.ReadWriteLock;
import java.util.concurrent.locks.ReentrantReadWriteLock;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.w3c.dom.Document;
import org.w3c.dom.Element;

import com.ibm.xml.parser.Parser;
import com.ibm.xml.parser.TXDocument;
import com.lblabs.xmltool.LBXMLOperator;
import com.lblabs.xmltool.SelectorByKeyTag;
import com.lblabs.xmltool.SelectorByTagAndWhere;
import com.lblabs.xmltool.SelectorForHash;

public class BookRetailer
{
	private static Log log = LogFactory.getLog(BookRetailer.class);
	String bookName;
	float bookPrice;
	String publisher;
	String author;
	float payment;
	int quantity;
	int inStock;
	float cash;
	String accountName;
	String accountPassword;
	String creditCardNumber;

	Hashtable orderFormHash = new Hashtable();
	Hashtable receiptFormHash = new Hashtable();

	String usrDir;

	String bookStoreXML;
	String accountInfoXML;
	String requestQueueXML;
	String responseQueueXML;
	String tempFile;
	String tempFileCopy;

	boolean requestFlag = false;
	boolean responseFlg = false;
	boolean bookStoreFlg = false;

	ReadWriteLock readWriteLock;
	Lock lock;

	public BookRetailer()
	{
		usrDir = System.getProperty("user.dir") + "/db/";
		tempFile = usrDir + "temp.txt";
		tempFileCopy = usrDir + "temp.copy.txt";
		bookStoreXML = usrDir + "book_store.xml";
		accountInfoXML = usrDir + "account_info.xml";
		requestQueueXML = usrDir + "request_queue.xml";
		responseQueueXML = usrDir + "response_queue.xml";
		readWriteLock = new ReentrantReadWriteLock();
		lock = readWriteLock.readLock();
	}

	public float getPrice(String bookName)
	{

		SelectorByTagAndWhere selectorByTagAndWhere = new SelectorByTagAndWhere();
		bookPrice = this.convertStringToFloat(selectorByTagAndWhere
				.selectByTagAndWhere(bookStoreXML, "BookName", "BookPrice",
						bookName));
		return bookPrice;
	}

	public String getPublisher(String bookName)
	{
		SelectorByTagAndWhere selectorByTagAndWhere = new SelectorByTagAndWhere();

		publisher = selectorByTagAndWhere.selectByTagAndWhere(bookStoreXML,
				"BookName", "BookPublisher", bookName);
		return publisher;
	}

	public String getAuthor(String bookName)
	{
		SelectorByTagAndWhere selectorByTagAndWhere = new SelectorByTagAndWhere();
		author = selectorByTagAndWhere.selectByTagAndWhere(bookStoreXML,
				"BookName", "BookAuthor", bookName);
		return author;
	}

	public Hashtable getRequest(String requestID)
	{
		SelectorByTagAndWhere selectorByTagAndWhere = new SelectorByTagAndWhere();
		Hashtable orderFormHash = new Hashtable();
		orderFormHash.put("RequestID", requestID);

		orderFormHash.put("InputAccountName", selectorByTagAndWhere
				.selectByTagAndWhere(requestQueueXML, "RequestID",
						"InputAccountName", requestID));
		orderFormHash.put("InputAccountPassword", selectorByTagAndWhere
				.selectByTagAndWhere(requestQueueXML, "RequestID",
						"InputAccountPassword", requestID));
		orderFormHash.put("InputCreditCardNumber", selectorByTagAndWhere
				.selectByTagAndWhere(requestQueueXML, "RequestID",
						"InputCreditCardNumber", requestID));
		orderFormHash.put("InputBookName", selectorByTagAndWhere
				.selectByTagAndWhere(requestQueueXML, "RequestID",
						"InputBookName", requestID));
		orderFormHash.put("InputQuantity", selectorByTagAndWhere
				.selectByTagAndWhere(requestQueueXML, "RequestID",
						"InputQuantity", requestID));

		return orderFormHash;
	}

	public Hashtable getResponse(String responseID)
	{
		SelectorByTagAndWhere selectorByTagAndWhere = new SelectorByTagAndWhere();
		Hashtable receiptFormHash = new Hashtable();
		receiptFormHash.put("ResponseID", responseID);

		receiptFormHash.put("Exception", selectorByTagAndWhere
				.selectByTagAndWhere(responseQueueXML, "ResponseID",
						"Exception", responseID));
		receiptFormHash.put("AccountName", selectorByTagAndWhere
				.selectByTagAndWhere(responseQueueXML, "ResponseID",
						"AccountName", responseID));
		receiptFormHash.put("BookName", selectorByTagAndWhere
				.selectByTagAndWhere(responseQueueXML, "ResponseID",
						"BookName", responseID));
		receiptFormHash.put("Quantity", selectorByTagAndWhere
				.selectByTagAndWhere(responseQueueXML, "ResponseID",
						"Quantity", responseID));
		receiptFormHash.put("CreditCardNumber", selectorByTagAndWhere
				.selectByTagAndWhere(responseQueueXML, "ResponseID",
						"CreditCardNumber", responseID));
		receiptFormHash.put("Payment", selectorByTagAndWhere
				.selectByTagAndWhere(responseQueueXML, "ResponseID", "Payment",
						responseID));

		return receiptFormHash;
	}

	public Hashtable buyBooks(String inputAccountName,
			String inputAccountPassword, String inputCreditCardNumber,
			String inputBookName, int inputQuantity)
	{
		System.out.println("\n\nbuyBooks starting ...");
		this.createRequestQueue(inputAccountName, inputAccountPassword,
				inputCreditCardNumber, inputBookName, this
						.convertIntToString(inputQuantity));
		SelectorForHash selectorForHash = new SelectorForHash();
		SelectorByTagAndWhere selectorByTagAndWhere = new SelectorByTagAndWhere();
		SelectorByKeyTag selectorByKeyTag = new SelectorByKeyTag();
		LBXMLOperator lbXMLOperator = new LBXMLOperator();
		Hashtable accountNameHash = selectorForHash.selectHash(accountInfoXML,
				"AccountName");
		if (accountNameHash.containsValue(inputAccountName))
		{
			accountPassword = selectorByTagAndWhere.selectByTagAndWhere(
					accountInfoXML, "AccountName", "AccountPassword",
					inputAccountName);
			if (inputAccountPassword.equals(accountPassword))
			{
				creditCardNumber = selectorByTagAndWhere.selectByTagAndWhere(
						accountInfoXML, "AccountName", "CreditCardNumber",
						inputAccountName);
				if (inputCreditCardNumber.equals(creditCardNumber))
				{
					Hashtable bookNameHash = selectorForHash.selectHash(
							bookStoreXML, "BookName");
					if (bookNameHash.containsValue(inputBookName))
					{
						inStock = this.convertStringToInt(selectorByTagAndWhere
								.selectByTagAndWhere(bookStoreXML, "BookName",
										"BookInStock", inputBookName));
						quantity = inputQuantity;
						if (inStock >= quantity)
						{
							bookPrice = this
									.convertStringToFloat(selectorByTagAndWhere
											.selectByTagAndWhere(bookStoreXML,
													"BookName", "BookPrice",
													inputBookName));
							inStock = inStock - quantity;
							cash = this.convertStringToFloat(selectorByKeyTag
									.selectByKeyTag(bookStoreXML, "Cash"));
							payment = bookPrice * quantity;
							cash = cash + payment;
							Hashtable keyTagHash = new Hashtable();
							Hashtable keyValueHash = new Hashtable();

							keyTagHash.put("0", "BookName");
							keyTagHash.put("1", "BookInStock");
							keyValueHash.put("0", inputBookName);
							keyValueHash.put("1", this
									.convertIntToString(inStock));

							lock = readWriteLock.writeLock();
							try
							{
								lock.tryLock();
								lbXMLOperator.changeByMultipleTagsAndWhere(
										bookStoreXML, keyTagHash, keyValueHash);
								lbXMLOperator.changeByTag(bookStoreXML, "Cash",
										this.convertFloatToString(cash));
							} catch (Exception e)
							{
								log.error(e);
							} finally
							{
								lock.unlock();
							}

							receiptFormHash.put("Exception", "Normal");
							receiptFormHash
									.put("AccountName", inputAccountName);
							receiptFormHash.put("BookName", inputBookName);
							receiptFormHash.put("Quantity", this
									.convertIntToString(quantity));
							receiptFormHash.put("Payment", this
									.convertFloatToString(payment));
							receiptFormHash.put("CreditCardNumber",
									inputCreditCardNumber);
							this.createResponseQueue("Normal",
									inputAccountName, inputBookName, this
											.convertIntToString(quantity), this
											.convertFloatToString(payment),
									inputCreditCardNumber);

						} else
						{
							receiptFormHash.put("Exception",
									"No enough book in stock!");
							this.createResponseQueue(
									"No enough book in stock!",
									inputAccountName, inputBookName, this
											.convertIntToString(quantity),
									"null", inputCreditCardNumber);
						}
					} else
					{
						receiptFormHash.put("Exception", "No such a book!");
						this.createResponseQueue("No such a book!",
								inputAccountName, inputBookName, this
										.convertIntToString(quantity), "null",
								inputCreditCardNumber);
					}
				} else
				{
					receiptFormHash.put("Exception",
							"Credit card number is not valid!");
					this.createResponseQueue(
							"Credit card number is not valid!",
							inputAccountName, inputBookName, this
									.convertIntToString(quantity), "null",
							inputCreditCardNumber);
				}
			} else
			{
				receiptFormHash.put("Exception", "Password is not correct!");
				this.createResponseQueue("Password is not correct!",
						inputAccountName, inputBookName, this
								.convertIntToString(quantity), "null",
						inputCreditCardNumber);
			}
		} else
		{
			receiptFormHash.put("Exception", "No such account name!");
			this.createResponseQueue("No such an account name!",
					inputAccountName, inputBookName, this
							.convertIntToString(quantity), "null",
					inputCreditCardNumber);
		}
		System.out.println("buyBooks ending ...\n\n");
		return receiptFormHash;
	}

	private void createRequestQueue(String inputAccountName,
			String inputAccountPassword, String inputCreditCardNumber,
			String inputBookName, String inputQuantity)
	{
		try
		{
			InputStream is = new FileInputStream(requestQueueXML);
			Parser parser = new Parser(requestQueueXML);
			Document doc;
			Element root;
			synchronized (parser)
			{
				doc = parser.readStream(is);
				root = doc.getDocumentElement();
			}

			Element requestItem;
			Element requestIDItem;
			Element inputAccountNameItem;
			Element inputAccountPasswordItem;
			Element inputCreditCardNumberItem;
			Element inputBookNameItem;
			Element inputQuantityItem;

			synchronized (doc)
			{
				requestItem = doc.createElement("Request");
				root.appendChild(requestItem);
			}

			SelectorForHash selectorForHash = new SelectorForHash();
			Hashtable requestIDHash = new Hashtable();
			requestIDHash = selectorForHash.selectHash(requestQueueXML,
					"RequestID");
			int requestIDSize = requestIDHash.size() + 1;

			// String str = null;
			lock = readWriteLock.writeLock();
			try
			{
				lock.lock();
				requestIDItem = doc.createElement("RequestID");
				requestIDItem.appendChild(doc.createTextNode(String
						.valueOf(requestIDSize)));
				requestItem.appendChild(requestIDItem);

				inputAccountNameItem = doc.createElement("InputAccountName");
				inputAccountNameItem.appendChild(doc
						.createTextNode(inputAccountName));
				requestItem.appendChild(inputAccountNameItem);

				inputAccountPasswordItem = doc
						.createElement("InputAccountPassword");
				inputAccountPasswordItem.appendChild(doc
						.createTextNode(inputAccountPassword));
				requestItem.appendChild(inputAccountPasswordItem);

				inputCreditCardNumberItem = doc
						.createElement("InputCreditCardNumber");
				inputCreditCardNumberItem.appendChild(doc
						.createTextNode(inputCreditCardNumber));
				requestItem.appendChild(inputCreditCardNumberItem);

				inputBookNameItem = doc.createElement("InputBookName");
				inputBookNameItem
						.appendChild(doc.createTextNode(inputBookName));
				requestItem.appendChild(inputBookNameItem);

				inputQuantityItem = doc.createElement("InputQuantity");
				inputQuantityItem
						.appendChild(doc.createTextNode(inputQuantity));
				requestItem.appendChild(inputQuantityItem);

				FileWriter fw = new FileWriter(requestQueueXML);
				PrintWriter pw = new PrintWriter(fw, true);

				((TXDocument) doc).printWithFormat(pw);
			} catch (Exception e)
			{
				// TODO: handle exception
			} finally
			{
				lock.unlock();
			}
		} catch (Exception e)
		{
			e.printStackTrace();
		}
	}

	private void createResponseQueue(String exception, String accountName,
			String bookName, String quantity, String payment,
			String creditCardNumber)
	{
		try
		{
			InputStream is = new FileInputStream(responseQueueXML);
			Parser parser = new Parser(responseQueueXML);
			Document doc;
			Element root;
			synchronized (parser)
			{
				doc = parser.readStream(is);
				root = doc.getDocumentElement();
			}

			Element responseItem = null;
			Element responseIDItem;
			Element exceptionItem;
			Element accountNameItem;
			Element bookNameItem;
			Element quantityItem;
			Element creditCardNumberItem;
			Element paymentItem;

			synchronized (doc)
			{
				responseItem = doc.createElement("Response");
				root.appendChild(responseItem);
			}

			SelectorForHash selectorForHash = new SelectorForHash();
			Hashtable responseIDHash = new Hashtable();
			responseIDHash = selectorForHash.selectHash(responseQueueXML,
					"ResponseID");
			int responseIDSize = responseIDHash.size();
			// String str = null;

			lock = readWriteLock.writeLock();

			try
			{
				lock.lock();
				responseIDItem = doc.createElement("ResponseID");
				responseIDItem.appendChild(doc.createTextNode(String
						.valueOf(responseIDSize)));
				responseItem.appendChild(responseIDItem);

				exceptionItem = doc.createElement("Exception");
				exceptionItem.appendChild(doc.createTextNode(exception));
				responseItem.appendChild(exceptionItem);

				accountNameItem = doc.createElement("AccountName");
				accountNameItem.appendChild(doc.createTextNode(accountName));
				responseItem.appendChild(accountNameItem);

				bookNameItem = doc.createElement("BookName");
				bookNameItem.appendChild(doc.createTextNode(bookName));
				responseItem.appendChild(bookNameItem);

				quantityItem = doc.createElement("Quantity");
				quantityItem.appendChild(doc.createTextNode(quantity));
				responseItem.appendChild(quantityItem);

				creditCardNumberItem = doc.createElement("CreditCardNumber");
				creditCardNumberItem.appendChild(doc
						.createTextNode(creditCardNumber));
				responseItem.appendChild(creditCardNumberItem);

				paymentItem = doc.createElement("Payment");
				paymentItem.appendChild(doc.createTextNode(payment));
				responseItem.appendChild(paymentItem);

				FileWriter fw = new FileWriter(responseQueueXML);
				PrintWriter pw = new PrintWriter(fw, true);

				((TXDocument) doc).printWithFormat(pw);

			} catch (Exception e)
			{
				// TODO: handle exception
			} finally
			{
				lock.unlock();
			}
		} catch (Exception e)
		{
			e.printStackTrace();
		}
	}

	private float convertStringToFloat(String floatStr)
	{
		Float bigFloat = new Float(floatStr);
		return bigFloat.floatValue();
	}

	private int convertStringToInt(String intStr)
	{
		Integer bigInt = new Integer(intStr);
		return bigInt.intValue();
	}

	private String convertIntToString(int intValue)
	{
		// String str = null;
		return String.valueOf(intValue);
	}

	private String convertFloatToString(float floatValue)
	{
		// String str = null;
		return String.valueOf(floatValue);
	}
}