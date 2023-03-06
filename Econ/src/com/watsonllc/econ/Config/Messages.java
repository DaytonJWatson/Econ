package com.watsonllc.econ.Config;

import java.io.File;
import java.io.IOException;

import org.bukkit.configuration.InvalidConfigurationException;
import org.bukkit.configuration.file.YamlConfiguration;

import com.watsonllc.econ.Economy;
import com.watsonllc.econ.Utils.Utils;

public class Messages {
	private static File messagesFile = new File(Economy.instance.getDataFolder(), "messages.yml");
	private static YamlConfiguration messages = YamlConfiguration.loadConfiguration(messagesFile);
	
	public static void create() {
		if(!messagesFile.exists()) {
			Economy.warning("Creating 'messages.yml'...");
			addDefaults();
			save(false);
		} else refresh(true);
	}
	
	public static void save(boolean announce) {
		try {
			if(announce) Economy.warning("Saving 'messages.yml'...");
			messages.save(messagesFile);
		} catch (IOException e) {
			Economy.warning("Failed to save 'messages.yml'!");
		}
	}
	
	public static void refresh(boolean announce) {
		try {
			if(announce) Economy.warning("Refreshing 'messages.yml'...");
			messages.load(messagesFile);
		} catch (IOException | InvalidConfigurationException e) {
			Economy.warning("Failed to refresh 'messages.yml'...");
		}
	}
	
	public static void set(String path, Object object) {
		messages.set(path, object);
	}
	
	public static String getString(String path) {
		return Utils.chat(messages.getString(path));
	}
	
	private static void addDefaults() {
		set("balance", "&7Balance: &a#CURRENCY##BALANCE#");
		set("balanceNeg", "&7Balance: &c#CURRENCY##BALANCE#");
		set("deposit", "&7You deposited: &a#CURRENCY##DEPOSIT#");
		set("loadPayment", "&7You paid: &c#CURRENCY#");
		set("loanWithdraw", "&7You took out a loan for: &c#CURRENCY#");
		set("taxesPaid", "&7You paid #CURRENCY##TAX# in taxes!");
		set("withdraw", "&7You withdrew: &c#CURRENCY##WITHDRAW#");
		set("invalid-args", "&cInvalid command syntax!");
		set("no-permission", "&cYou don\\'t have permission to do that.");
	}
}
