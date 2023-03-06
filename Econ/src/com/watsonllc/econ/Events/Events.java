package com.watsonllc.econ.Events;

import org.bukkit.plugin.PluginManager;

import com.watsonllc.econ.Economy;
import com.watsonllc.econ.Events.player.Interact;
import com.watsonllc.econ.Events.player.Join;

public class Events {
	public static void setup() {
		PluginManager pm = Economy.instance.getServer().getPluginManager();
		
		pm.registerEvents(new Interact(), Economy.instance);
		pm.registerEvents(new Join(), Economy.instance);
	}
}
