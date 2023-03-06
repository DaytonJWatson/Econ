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
	
	//placeholders
	double lastWithdraw;
	double lastDeposit;
	double lastTaxPaid;

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
		setLastDeposit(round(net));
		setLastTax(taxMath(amount));
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
		setLastDeposit(round(newBal));
	}

	public void withdraw(double amount) {
		Bank.refresh(false);
		this.balance = getBalance();
		double newBal = balance - amount;
		Bank.set(dataPath + ".balance", round(newBal));
		setLastWithdraw(round(newBal));
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
	
	public void setLastWithdraw(double amount) {
		this.lastWithdraw = amount;
	}
	
	public void setLastDeposit(double amount) {
		this.lastWithdraw = amount;
	}
	
	public void setLastTax(double amount) {
		this.lastTaxPaid = amount;
	}
	
	public double getLastWithdraw() {
		return lastWithdraw;
	}
	
	public double getLastDeposit() {
		return lastDeposit;
	}
	
	public double getLastTaxPaid() {
		return lastTaxPaid;
	}
	
	public String placeholders(String message) {
		String placeholder;
		placeholder = message;
		placeholder = placeholder.replace("#BALANCE#", String.valueOf(getBalance()));
		placeholder = placeholder.replace("#CURRENCY#", String.valueOf(getCurrency()));
		placeholder = placeholder.replace("#OWNER#", player.getName());
		placeholder = placeholder.replace("#TAX#", String.valueOf(getLastTaxPaid()));
		placeholder = placeholder.replace("#WITHDRAW#", String.valueOf(getLastWithdraw()));
		placeholder = placeholder.replace("#DEPOSIT#", String.valueOf(getLastDeposit()));
		
		return placeholder;
	}
	
	private static double round(double num) {
		return Math.round(num * 100.0) / 100.0;
	}
	
	// static api section

	public static boolean isTaxCollected() {
		return Config.getBoolean("bank.taxEnabled");
	}
	
	public static boolean isTaxCollected(Player player) {
		if(isTaxExempt(player)) return false;
		if(isTaxCollected() && isTaxExempt(player)) return false;
		if(isTaxCollected() && !isTaxExempt(player)) return true;
		return Config.getBoolean("bank.taxEnabled");
	}

	public static boolean isTaxExempt(Player player) {
		AccountManager account = new AccountManager(player);
		return account.getTaxExempt();
	}
	
	public static double getWithdrawAmount(Player player) {
		AccountManager account = new AccountManager(player);
		return account.getLastWithdraw();
	}
	
	public static double getDepositAmount(Player player) {
		AccountManager account = new AccountManager(player);
		return account.getLastDeposit();
	}
	
	public static void collectTax(Player player, double amount) {
		AccountManager account = new AccountManager(player);
		if(isTaxCollected() && !isTaxExempt(player)) {
			account.depositWithTax(amount);
		} else return;
	}
	
	public static double balance(Player player) {
		AccountManager account = new AccountManager(player);
		return account.getBalance();
	}
	
	public static boolean hasNegativeBalance(Player player) {
		AccountManager account = new AccountManager(player);
		if(account.getBalance() < 0) {
			return true;
		} else return false;
	}
	
	public static boolean hasEnough(Player player, double amount) {
		AccountManager account = new AccountManager(player);
		double balance;
		balance = account.getBalance();
		if (balance < amount)
			return false;
		return true;
	}
	
	public static void transferFromBank(Player player, double amount) {
		AccountManager account = new AccountManager(player);
		BankManager bank = new BankManager();
		account.deposit(amount);
		bank.withdraw(amount);
	}
	
	public static void transferToBank(Player player, double amount) {
		AccountManager account = new AccountManager(player);
		BankManager bank = new BankManager();
		account.withdraw(amount);
		bank.deposit(amount);
	}
	
	public static void transfer(Player from, Player to, double amount) {
		AccountManager fromAccount = new AccountManager(from);
		AccountManager toAccount = new AccountManager(to);
		fromAccount.withdraw(amount);
		toAccount.deposit(amount);
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
