package com.watsonllc.econ.Commands.player;

import org.bukkit.command.Command;
import org.bukkit.command.CommandExecutor;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;

public class Loan implements CommandExecutor {

	@Override
	public boolean onCommand(CommandSender sender, Command cmd, String arg2, String[] args) {
		Player player = (Player) sender;
		
		if(cmd.getName().equalsIgnoreCase("loan") && sender.hasPermission("economy.loan")) {
			
		} else {
			player.sendMessage("no permission");
		}
		return false;
	}

}
