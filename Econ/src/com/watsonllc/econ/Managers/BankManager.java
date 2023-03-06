package com.watsonllc.econ.Managers;

import org.bukkit.entity.Player;

import com.watsonllc.econ.Config.Bank;

public class BankManager {
	double balance;
	double taxRate;
	String dataPath;
	
	public BankManager() {
		this.balance = Bank.getDouble("bank.defaultBalance");
		this.taxRate = Bank.getDouble("bank.defaultTaxRate");
		this.dataPath = "Bank";
	}
	
	public void pay(Player player, double amount) {
		AccountManager am = new AccountManager(player);
		am.deposit(amount);
		withdraw(amount);
	}
	
	public boolean hasSufficientFunds(double amount) {
		Bank.refresh(false);
		this.balance = getBalance();
		if (balance < amount)
			return false;
		return true;
	}
	
	public void deposit(double amount) {
		this.balance = getBalance();
		double newBal = balance + amount;
		Bank.set(dataPath + ".balance", round(newBal));
	}
	
	public void withdraw(double amount) {
		Bank.refresh(false);
		this.balance = getBalance();
		double bal = balance - amount;
		Bank.set(dataPath + ".balance", round(bal));
	}
	
	public void setBalance(double balance) {
		this.balance = balance;
		Bank.set(dataPath + ".balance", balance);
	}
	
	public double getBalance() {
		Bank.refresh(false);
		this.balance = Bank.getDouble(dataPath + ".balance");
		return round(balance);
	}
	
	private static double round(double num) {
		return Math.round(num * 100.0) / 100.0;
	}
}
