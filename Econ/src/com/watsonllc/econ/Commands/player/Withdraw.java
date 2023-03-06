package com.watsonllc.econ.Commands.player;

import org.bukkit.command.Command;
import org.bukkit.command.CommandExecutor;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;

import com.watsonllc.econ.Managers.AccountManager;
import com.watsonllc.econ.Managers.PhysicalManager;

public class Withdraw  implements CommandExecutor {
	@Override
	public boolean onCommand(CommandSender sender, Command cmd, String arg2, String[] args) {
		Player player = (Player) sender;
		AccountManager bank = new AccountManager(player);
		PhysicalManager coin = new PhysicalManager('$');
		Double withdrawAmount;
		
		if(cmd.getName().equalsIgnoreCase("withdraw") && sender.hasPermission("economy.withdraw")) {
			if(args.length == 1) {
				withdrawAmount = Double.valueOf(args[0]);
				if(bank.hasSufficientFunds(withdrawAmount)) {
					bank.withdraw(withdrawAmount);
					player.getInventory().addItem(coin.createCoin(withdrawAmount));
					player.sendMessage("You withdrew: $" + withdrawAmount);
				} else player.sendMessage("You cant afford that!");
			}
		}
		return false;
	}
}
