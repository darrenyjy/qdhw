package com.lblabs.business;

public class Bank
{
	BankDB bankDB;
	public Bank()
	{
		bankDB = new BankDB();
	}

	public float getAccountBalance(String accountName)
	{
		return bankDB.getAccountBalance(accountName);
	}

	public float getTotalDeposit()
	{
		return bankDB.getTotalDeposit();
	}

	public float deposit(String accountName, float accountBalance, float depositAmount)
	{
		accountBalance += depositAmount;
		float totalDeposit = bankDB.getTotalDeposit();
		totalDeposit += depositAmount;
		bankDB.saveBalance(accountName, accountBalance);
		bankDB.saveTotalDeposit(totalDeposit);
		return accountBalance;
	}

	public float withdraw(String accountName, float accountBalance, float withdrawAmount)
	{
		accountBalance = accountBalance - withdrawAmount;
		float totalDeposit = bankDB.getTotalDeposit();
		totalDeposit = totalDeposit - withdrawAmount;
		bankDB.saveBalance(accountName, accountBalance);
		bankDB.saveTotalDeposit(totalDeposit);
		return accountBalance;
	}
}