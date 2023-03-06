package com.watsonllc.econ.Config;

import com.watsonllc.econ.Economy;
import com.watsonllc.econ.Utils.Utils;

public class Config {
	public static void setup() {
		create();
		Bank.create();
		Messages.create();
	}

	public static void create() {
		Economy.warning("Loading 'config.yml'...");
		Economy.instance.getConfig().options().copyDefaults(true);
		Economy.instance.saveDefaultConfig();
	}

	public static void save(boolean announce) {
		if (announce)
			Economy.warning("Saving 'config.yml'...");
		Economy.instance.saveConfig();
	}

	public static void reload(boolean announce) {
		if (announce)
			Economy.warning("Reloading 'config.yml'");
		Economy.instance.reloadConfig();
	}
	
	public static String getString(String path) {
		return Utils.chat(Economy.instance.getConfig().getString(path));
	}

	public static int getInt(String path) {
		return Economy.instance.getConfig().getInt(path);
	}

	public static double getDouble(String path) {
		return Economy.instance.getConfig().getDouble(path);
	}

	public static boolean getBoolean(String path) {
		return Economy.instance.getConfig().getBoolean(path);
	}
}
