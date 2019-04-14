package me.flail.microitems;

import java.io.IOException;
import java.util.List;

import org.bukkit.command.Command;
import org.bukkit.command.CommandSender;
import org.bukkit.command.ConsoleCommandSender;
import org.bukkit.plugin.java.JavaPlugin;

import me.flail.microitems.utilities.Config;
import me.flail.microitems.utilities.TabCompleter;
import me.flail.microitems.utilities.Utilities;

public class MicroItems extends JavaPlugin {
	public Utilities utilities = Utilities.get();
	public ConsoleCommandSender console = getServer().getConsoleSender();

	@Override
	public void onEnable() {
		long loadTime = System.currentTimeMillis();
		console.sendMessage(utilities.chat("\n &eInitializing startup of MicroItems..."));

		try {
			new Config(this).load();
			console.sendMessage(utilities.chat(" &7Loaded Settings.yml file."));
		} catch (IOException e) {
			getLogger().warning(
					"Couldn't load settings file! Try Restarting your server."
							+ "\nIf this persists, delete the Settings.yml file in this plugin's data folder; and restart.");
		}

		registerCommands();

		console.sendMessage(utilities.chat(" &aStartup complete. &8(&7" + (System.currentTimeMillis() - loadTime) + "&8)\n"));
	}

	@Override
	public void onDisable() {
		getServer().getScheduler().cancelTasks(this);
		console.sendMessage(utilities.chat(" &aShutdown success.  &eGoodbye!"));
	}

	private void registerCommands() {
		for (String command : this.getDescription().getCommands().keySet()) {
			this.getCommand(command).setExecutor(this);
			console.sendMessage(utilities.chat(" &7Registered command: &e/" + command));
		}
	}

	@Override
	public boolean onCommand(CommandSender sender, Command command, String label, String[] args) {
		return false;
	}

	@Override
	public List<String> onTabComplete(CommandSender sender, Command command, String label, String[] args) {
		return new TabCompleter(this).construct(command, args);
	}

}
