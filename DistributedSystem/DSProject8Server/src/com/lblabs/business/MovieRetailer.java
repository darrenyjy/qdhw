/**
 * 
 */
package com.lblabs.business;

import java.util.Enumeration;
import java.util.Hashtable;

import com.lblabs.tools.Tools;

/**
 * @author Coddy created on 2010-7-10
 * 
 */
public class MovieRetailer
{
	String movieName;
	float moviePrice;
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

	Hashtable shoppingCartHash = new Hashtable();

	MovieRetailerDB movieRetailerDB;
	Tools tools = new Tools();

	public MovieRetailer()
	{
		movieRetailerDB = new MovieRetailerDB();
	}

	public boolean signUp(String newAccountName, String newAccountPassword,
			String newCreditCardNumber)
	{
		if (!movieRetailerDB.isAccountNameExisted(newAccountName))
		{
			if (movieRetailerDB.saveAccount(newAccountName, newAccountPassword,
					newCreditCardNumber))
			{
				accountName = newAccountName;
				accountPassword = newAccountPassword;
				creditCardNumber = newCreditCardNumber;
				return true;
			} else
			{
				return false;
			}
		} else
		{
			return true;
		}
	}

	public boolean signIn(String inputAccountName, String inputAccountPassword)
	{
		if (movieRetailerDB.isAccountMatched(inputAccountName,
				inputAccountPassword))
		{
			accountName = inputAccountName;
			accountPassword = inputAccountPassword;
			return true;
		} else
		{
			return false;
		}
	}

	public float getPrice(String movieName)
	{
		return movieRetailerDB.getPrice(movieName);
	}

	public String getPublisher(String movieName)
	{
		return movieRetailerDB.getPublisher(movieName);
	}

	public String getAuthor(String movieName)
	{
		return movieRetailerDB.getAuthor(movieName);
	}

	public boolean putIntoShoppingCart(String movieName, int quantity)
	{
		if (movieRetailerDB.isMovieExisted(movieName))
		{
			if (!shoppingCartHash.containsKey(movieName))
			{
				shoppingCartHash.put(movieName, tools
						.convertIntToString(quantity));
			} else
			{
				int currentQuantity = tools
						.convertStringToInt((String) shoppingCartHash
								.get(movieName));
				currentQuantity += quantity;
				shoppingCartHash.put(movieName, tools
						.convertIntToString(currentQuantity));
			}
		} else
		{
			return false;
		}
		return true;
	}

	public boolean moveOutShoppingCart(String movieName, int quantity)
	{
		if (shoppingCartHash.containsKey(movieName))
		{
			int currentQuantity = tools
					.convertStringToInt((String) shoppingCartHash
							.get(movieName));
			if (currentQuantity > quantity)
			{
				currentQuantity = currentQuantity - quantity;
				shoppingCartHash.put(movieName, tools
						.convertIntToString(currentQuantity));
			} else
			{
				shoppingCartHash.remove(movieName);
			}
			return true;
		} else
		{
			return false;
		}
	}

	public Hashtable getShoppingCart()
	{
		return shoppingCartHash;
	}

	public Hashtable buyMovies(String inputCreditCardNumber)
	{
		String str = null;
		String soldMovies = "";
		payment = 0;
		// System.out.println("shoppingCartHash = " + shoppingCartHash);
		// for (int i = 0; i < shoppingCartHash.size(); i ++)
		Enumeration shoppingCartEnum = shoppingCartHash.keys();

		String movieName;

		while (shoppingCartEnum.hasMoreElements())
		{
			movieName = (String) shoppingCartEnum.nextElement();
			quantity = tools.convertStringToInt((String) shoppingCartHash
					.get(movieName));
			// selectedMovie = (SelectedMovie)shoppingCartHash.get(movieName);
			// System.out.println("selectedMovie.movieName = " +
			// selectedMovie.movieName);
			// System.out.println("selectedMovie.quantity = " +
			// selectedMovie.quantity);
			inStock = movieRetailerDB.getInstock(movieName);
			if (inStock >= quantity)
			{
				moviePrice = movieRetailerDB.getPrice(movieName);
				inStock = inStock - quantity;
				cash = movieRetailerDB.getCash();
				payment += moviePrice * quantity;
				cash = cash + payment;
				movieRetailerDB.updateInstock(movieName, inStock);
				movieRetailerDB.updateCash(cash);
				soldMovies += movieName + "#";
			}
		}
		receiptFormHash.put("Exception", "Normal");
		receiptFormHash.put("AccountName", accountName);
		receiptFormHash.put("MovieName", soldMovies);
		receiptFormHash.put("Quantity", tools.convertIntToString(quantity));
		receiptFormHash.put("Payment", tools.convertFloatToString(payment));
		receiptFormHash.put("CreditCardNumber", inputCreditCardNumber);
		return receiptFormHash;
	}
}
