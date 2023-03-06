package com.watsonllc.econ.Events.player;

import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.block.Action;
import org.bukkit.event.player.PlayerInteractEvent;
import org.bukkit.inventory.ItemStack;

import com.watsonllc.econ.Config.Messages;
import com.watsonllc.econ.Managers.AccountManager;
import com.watsonllc.econ.Managers.PhysicalManager;

public class Interact implements Listener {

	@EventHandler
	public void onPlayerInteract(PlayerInteractEvent event) {
		Player player = event.getPlayer();
		ItemStack hand = player.getInventory().getItemInMainHand();
		AccountManager account = new AccountManager(player);
		String deposit;
		deposit = account.placeholders(Messages.getString("deposit"));

		if(PhysicalManager.isHoldingCoin(hand)) {
			if (event.getAction().equals(Action.RIGHT_CLICK_AIR) || event.getAction().equals(Action.RIGHT_CLICK_BLOCK)) {
				PhysicalManager.cashOutCoin(player);
				player.sendMessage(deposit);
			}
		}
	}
}
