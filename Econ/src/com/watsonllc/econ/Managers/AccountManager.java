package com.watsonllc.econ.Managers;

import org.bukkit.entity.Player;

import com.watsonllc.econ.Config.Bank;
import com.watsonllc.econ.Config.Config;

public class AccountManager {
	Player player;
	String dataPath;
	String name;
	char currency;
	double balance;
	boolean taxExempt;

	public AccountManager(Player player) {
		this.player = player;
		this.currency = Config.getString("bank.defaultCurrency").charAt(0);
		this.taxExempt = false;
		if (player != null)
			this.dataPath = "Bank.accounts." + player.getUniqueId().toString();
	}
	
	public AccountManager(Player player, char currency) {
		this.player = player;
		this.currency = currency;
		this.taxExempt = false;
		if (player != null)
			this.dataPath = "Bank.accounts." + player.getUniqueId().toString();
	}

	public AccountManager(Player player, char currency, boolean taxExempt) {
		this.player = player;
		this.currency = currency;
		this.taxExempt = taxExempt;
		if (player != null)
			this.dataPath = "Bank.accounts." + player.getUniqueId().toString();
	}

	public boolean accountNull() {
		if (Bank.getString(dataPath) == null) {
			return true;
		} else
			return false;
	}

	public void createAccount(double balance) {
		this.balance = balance;
		this.name = player.getName();

		Bank.set(dataPath + ".user", name);
		Bank.set(dataPath + ".currency", String.valueOf(currency));
		Bank.set(dataPath + ".balance", balance);
		Bank.set(dataPath + ".taxExempt", taxExempt);
	}

	public boolean hasSufficientFunds(double amount) {
		this.balance = getBalance();
		if (balance < amount)
			return false;
		return true;
	}
	
	public void depositWithTax(double amount) {
		double net;
		net = netMath(amount);
		this.balance = getBalance();
		double newBal = balance + net;
		Bank.set(dataPath + ".balance", round(newBal));
	}
	
	public void payBank(double amount) {
		BankManager bank = new BankManager();
		bank.deposit(amount);
		withdraw(amount);
	}

	public void deposit(double amount) {
		this.balance = getBalance();
		double newBal = balance + amount;
		Bank.set(dataPath + ".balance", round(newBal));
	}

	public void withdraw(double amount) {
		Bank.refresh(false);
		this.balance = getBalance();
		double newBal = balance - amount;
		Bank.set(dataPath + ".balance", round(newBal));
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
	
	public char getCurrency() {
		String string = Bank.getString(dataPath + ".currency");
		this.currency = string.charAt(0);
		return currency;
	}

	public boolean getTaxExempt() {
		Bank.refresh(false);
		this.taxExempt = Bank.getBoolean(dataPath + ".taxExempt");
		return taxExempt;
	}
	
	public String placeholders(String message) {
		String placeholder;
		placeholder = message;
		placeholder = placeholder.replace("#BALANCE#", String.valueOf(getBalance()));
		placeholder = placeholder.replace("#CURRENCY#", String.valueOf(getCurrency()));
		placeholder = placeholder.replace("#OWNER#", player.getName());
		placeholder = placeholder.replace("#AMOUNT#", "");
		return placeholder;
	}
	
	private static double round(double num) {
		return Math.round(num * 100.0) / 100.0;
	}
	
	// static api section

	public static boolean isTaxCollected() {
		return Config.getBoolean("bank.taxEnabled");
	}

	public static boolean isTaxExempt(Player player) {
		AccountManager bank = new AccountManager(player);
		return bank.getTaxExempt();
	}
	
	public static double balance(Player player) {
		AccountManager bank = new AccountManager(player);
		return bank.getBalance();
	}
	
	public boolean hasEnough(Player player, double amount) {
		AccountManager bank = new AccountManager(player);
		double balance;
		balance = bank.getBalance();
		if (balance < amount)
			return false;
		return true;
	}
	
	public static double netMath(double gross) {
		double tax;
		double net;
		tax = taxMath(gross);
		net = gross - tax;
		return round(net);
	}

	public static double taxMath(double gross) {
		double tax;
		double rate = Bank.taxRate();
		tax = gross * rate;
		return round(tax);
	}
}
