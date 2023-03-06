package com.watsonllc.econ.Commands.player;

import org.bukkit.command.Command;
import org.bukkit.command.CommandExecutor;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;

import com.watsonllc.econ.Config.Messages;
import com.watsonllc.econ.Managers.AccountManager;
import com.watsonllc.econ.Managers.PhysicalManager;

public class Withdraw  implements CommandExecutor {
	@Override
	public boolean onCommand(CommandSender sender, Command cmd, String arg2, String[] args) {
		Player player = (Player) sender;
		AccountManager account = new AccountManager(player);
		PhysicalManager coin = new PhysicalManager('$');
		Double withdrawAmount;
		String withdraw;
		withdraw = account.placeholders(Messages.getString("withdraw"));
		
		if(cmd.getName().equalsIgnoreCase("withdraw") && sender.hasPermission("economy.withdraw")) {
			if(args.length == 1) {
				withdrawAmount = Double.valueOf(args[0]);
				if(account.hasSufficientFunds(withdrawAmount)) {
					account.withdraw(withdrawAmount);
					player.getInventory().addItem(coin.createCoin(withdrawAmount));
					player.sendMessage(withdraw);
				} else player.sendMessage("You cant afford that!");
			}
		}
		return false;
	}
}
