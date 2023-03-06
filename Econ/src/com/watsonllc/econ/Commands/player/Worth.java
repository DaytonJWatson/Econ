package com.watsonllc.econ.Commands.player;

import org.bukkit.command.Command;
import org.bukkit.command.CommandExecutor;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;
import org.bukkit.inventory.ItemStack;

import com.watsonllc.econ.Managers.PhysicalManager;

public class Worth implements CommandExecutor {
	@Override
	public boolean onCommand(CommandSender sender, Command cmd, String arg2, String[] args) {
		Player player = (Player) sender;
		ItemStack item = player.getInventory().getItemInMainHand();
		double worth;
		
		if(cmd.getName().equalsIgnoreCase("worth") && sender.hasPermission("")) {
			if(PhysicalManager.isCoin(item)) {
				worth = PhysicalManager.getCoinWorth(item);
				player.sendMessage("That coin is worth $" + worth);
			} else {
				player.sendMessage("That isn't a coin.");
			}
		}
		return false;
	}

}
