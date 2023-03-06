package com.watsonllc.econ.Commands.admin;

import org.bukkit.command.Command;
import org.bukkit.command.CommandExecutor;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;

import com.watsonllc.econ.Config.Bank;
import com.watsonllc.econ.Config.Config;
import com.watsonllc.econ.Config.Messages;

public class Refresh implements CommandExecutor {
	@Override
	public boolean onCommand(CommandSender sender, Command cmd, String arg2, String[] arg3) {
		Player player = (Player) sender;
		
		if(cmd.getName().equalsIgnoreCase("refresh")) {
			player.sendMessage("refreshing bank.yml, config.yml, messages.yml");
			Bank.refresh(true);
			Config.reload(true);
			Messages.refresh(true);
			
		}
		return false;
	}
}
