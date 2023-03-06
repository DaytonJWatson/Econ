package com.watsonllc.econ.Config;

import java.io.File;
import java.io.IOException;

import org.bukkit.configuration.InvalidConfigurationException;
import org.bukkit.configuration.file.YamlConfiguration;

import com.watsonllc.econ.Economy;

public class Bank {
	private static File bankFile = new File(Economy.instance.getDataFolder(), "bank.yml");
	private static YamlConfiguration bank = YamlConfiguration.loadConfiguration(bankFile);

	public static void create() {
		if (!bankFile.exists()) {
			addDefaults();
			save(false);
			Economy.warning("Creating 'bank.yml'...");
		} else refresh(true);
	}

	public static void save(boolean announce) {
		try {
			if(announce) Economy.warning("Saving 'bank.yml'...");
			bank.save(bankFile);
		} catch (IOException e) {
			Economy.warning("Failed to save 'bank.yml'!");
		}
	}
	
	public static void refresh(boolean announce) {
		try {
			if(announce) Economy.warning("Refreshing 'bank.yml'...");
			bank.load(bankFile);
		} catch (IOException | InvalidConfigurationException e) {
			Economy.warning("Failed to refresh 'bank.yml'!");
		}
	}

	private static void addDefaults() {
		set("Bank.balance", Config.getInt("bank.defaultBalance"));
		set("Bank.taxRate", Config.getDouble("bank.defaultTaxRate"));
	}
	
	public static double balance(String path) {
		return getDouble(path);
	}
	
	public static double taxRate() {
		return getDouble("Bank.taxRate");
	}
	
	public static double getDouble(String path) {
		return bank.getDouble(path);
	}
	
	public static String getString(String path) {
		return bank.getString(path);
	}
	
	public static boolean getBoolean(String path) {
		return bank.getBoolean(path);
	}
	
	public static void set(String path, Object object) {
		bank.set(path, object);
		save(false);
	}
}