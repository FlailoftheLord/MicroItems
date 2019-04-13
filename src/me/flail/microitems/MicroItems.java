package me.flail.microitems;

import java.util.List;

import org.bukkit.command.Command;
import org.bukkit.command.CommandSender;
import org.bukkit.plugin.java.JavaPlugin;

import me.flail.microitems.utilities.Config;
import me.flail.microitems.utilities.TabCompleter;

public class MicroItems extends JavaPlugin {

	@Override
	public void onEnable() {

		new Config(this).load();

	}

	@Override
	public void onDisable() {

	}

	@Override
	public boolean onCommand(CommandSender sender, Command command, String label, String[] args) {
		return false;
	}

	@Override
	public List<String> onTabComplete(CommandSender sender, Command command, String label, String[] args) {
		return new TabCompleter(this).construct();
	}

}
