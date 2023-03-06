package com.watsonllc.econ;

import org.bukkit.plugin.java.JavaPlugin;

import com.watsonllc.econ.Commands.Commands;
import com.watsonllc.econ.Config.Config;
import com.watsonllc.econ.Events.Events;

public class Economy extends JavaPlugin {
	public static Economy instance;
	
	@Override
	public void onEnable() {
		instance = this;
		
		Config.setup();
		Commands.setup();
		Events.setup();
	}
	
	public static void warning(String warn) {
		instance.getLogger().warning(warn);
	}
}