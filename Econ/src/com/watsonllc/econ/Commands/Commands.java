package com.watsonllc.econ.Commands;

import com.watsonllc.econ.Economy;
import com.watsonllc.econ.Commands.admin.Coin;
import com.watsonllc.econ.Commands.admin.Refresh;
import com.watsonllc.econ.Commands.player.Balance;
import com.watsonllc.econ.Commands.player.Deposit;
import com.watsonllc.econ.Commands.player.Withdraw;
import com.watsonllc.econ.Commands.player.Worth;

public class Commands {
	public static void setup() {
		//player
		Economy.instance.getCommand("balance").setExecutor(new Balance());
		Economy.instance.getCommand("deposit").setExecutor(new Deposit());
		Economy.instance.getCommand("withdraw").setExecutor(new Withdraw());
		Economy.instance.getCommand("worth").setExecutor(new Worth());
		
		//admin
		Economy.instance.getCommand("coin").setExecutor(new Coin());
		Economy.instance.getCommand("refresh").setExecutor(new Refresh());
	}
}
