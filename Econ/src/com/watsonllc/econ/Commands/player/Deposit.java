package com.watsonllc.econ.Commands.player;

import org.bukkit.command.Command;
import org.bukkit.command.CommandExecutor;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;

import com.watsonllc.econ.Managers.PhysicalManager;

public class Deposit implements CommandExecutor {

	@Override
	public boolean onCommand(CommandSender sender, Command cmd, String arg2, String[] args) {
		Player player = (Player) sender;
		
		if(cmd.getName().equalsIgnoreCase("deposit") && sender.hasPermission("economy.deposit")) {
			PhysicalManager.cashOutCoin(player);
		}
		return false;
	}
}
