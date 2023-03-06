package com.watsonllc.econ.Commands.admin;

import org.bukkit.command.Command;
import org.bukkit.command.CommandExecutor;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;

import com.watsonllc.econ.Managers.PhysicalManager;

public class Coin implements CommandExecutor {
	@Override
	public boolean onCommand(CommandSender sender, Command cmd, String arg2, String[] args) {
		Player player = (Player) sender;
		
		PhysicalManager coin = new PhysicalManager('$');
		
		if(cmd.getName().equalsIgnoreCase("coin")) {
			if(args.length == 1) {
				player.getInventory().addItem(coin.createCoin(Double.valueOf(args[0])));
			} else player.sendMessage("error");
		}
		return false;
	}

}
