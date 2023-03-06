package com.watsonllc.econ.Commands.player;

import org.bukkit.command.Command;
import org.bukkit.command.CommandExecutor;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;

import com.watsonllc.econ.Config.Messages;
import com.watsonllc.econ.Managers.AccountManager;
import com.watsonllc.econ.Managers.PhysicalManager;

public class Deposit implements CommandExecutor {

	@Override
	public boolean onCommand(CommandSender sender, Command cmd, String arg2, String[] args) {
		Player player = (Player) sender;
		AccountManager account = new AccountManager(player);
		String deposit;
		String tax;
		deposit = account.placeholders(Messages.getString("deposit"));
		tax = account.placeholders(Messages.getString("taxesPaid"));
		
		if(cmd.getName().equalsIgnoreCase("deposit") && sender.hasPermission("economy.deposit")) {
			PhysicalManager.cashOutCoin(player);
			player.sendMessage(deposit);
			if(AccountManager.isTaxCollected()) {
				player.sendMessage(tax);
			}
		}
		return false;
	}
}
