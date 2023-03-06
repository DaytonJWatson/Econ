package com.watsonllc.econ.Managers;

import java.util.ArrayList;
import java.util.List;

import org.bukkit.Material;
import org.bukkit.entity.Player;
import org.bukkit.inventory.ItemStack;
import org.bukkit.inventory.meta.ItemMeta;

import com.watsonllc.econ.Config.Config;
import com.watsonllc.econ.Utils.Utils;

public class PhysicalManager {
	ItemStack physical;
	Material material;
	char currency;
	double worth;

	public PhysicalManager(char currency) {
		this.material = Material.valueOf(Config.getString("physicalCurrency.material"));
		this.worth = 0.0;
		this.currency = currency;
	}

	public ItemStack createCoin(double amount) {
		this.worth = amount;
		ItemStack physical = new ItemStack(material);
		ItemMeta meta = physical.getItemMeta();
		List<String> lore = new ArrayList<String>();
		lore.add(Utils.chat("&f" + currency + worth));

		meta.setDisplayName(Config.getString("physicalCurrency.name"));
		meta.setLore(lore);
		meta.setLocalizedName("physicalCurrency=" + worth);
		physical.setItemMeta(meta);
		this.physical = physical;
		return physical;
	}

	// api section

	public static void cashOutCoin(Player player) {
		ItemStack hand = player.getInventory().getItemInMainHand();
		AccountManager bm = new AccountManager(player);
		double deposited;
		double tax;
		deposited = PhysicalManager.getCoinWorth(hand);
		tax = AccountManager.taxMath(deposited);

		if (PhysicalManager.isHoldingCoin(hand)) {
			hand.setType(Material.AIR);
			if (AccountManager.isTaxCollected() && !AccountManager.isTaxExempt(player)) {
				player.sendMessage("You deposited: $" + deposited);
				player.sendMessage("You paid $" + tax + " in taxes!");
				bm.depositWithTax(deposited);
			} else {
				bm.deposit(deposited);
				player.sendMessage("You deposited: $" + deposited);
			}
		} else return;
	}

	public static double getCoinWorth(ItemStack item) {
		ItemMeta meta = item.getItemMeta();
		double worth = Double.parseDouble(meta.getLocalizedName().replace("physicalCurrency=", ""));
		return worth;
	}

	public static boolean isHoldingCoin(ItemStack item) {
		ItemMeta meta = item.getItemMeta();
		if (meta == null)
			return false;
		if (meta.getLocalizedName().contains("physicalCurrency=")) {
			return true;
		}
		return false;
	}
}
