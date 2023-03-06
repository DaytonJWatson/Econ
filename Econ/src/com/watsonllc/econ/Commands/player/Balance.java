package com.watsonllc.econ.Commands.player;

import org.bukkit.command.Command;
import org.bukkit.command.CommandExecutor;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;

import com.watsonllc.econ.Config.Messages;
import com.watsonllc.econ.Managers.AccountManager;

public class Balance implements CommandExecutor {
	@Override
	public boolean onCommand(CommandSender sender, Command cmd, String arg2, String[] args) {
		Player player = (Player) sender;
		AccountManager bm = new AccountManager(player);
		String balance;
		String balanceNeg;
		balance = bm.placeholders(Messages.getString("balance"));
		balanceNeg = bm.placeholders(Messages.getString("balanceNeg"));
		
		if(cmd.getName().equalsIgnoreCase("balance") && sender.hasPermission("economy.balance")) {
			if(bm.getBalance() > 0) player.sendMessage(balance);
			if(bm.getBalance() <= 0) player.sendMessage(balanceNeg);
		}
		return false;
	}
}
