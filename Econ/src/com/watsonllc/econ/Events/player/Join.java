package com.watsonllc.econ.Events.player;

import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.player.PlayerJoinEvent;

import com.watsonllc.econ.Managers.AccountManager;

public class Join implements Listener {
	@EventHandler
	public void onPlayerJoin(PlayerJoinEvent event) {
		Player player = event.getPlayer();
		
		AccountManager bank = new AccountManager(player);
		
		if(bank.accountNull()) bank.createAccount(100);
	}
}
