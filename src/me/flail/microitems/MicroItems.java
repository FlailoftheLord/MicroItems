package me.flail.microitems;

import java.util.HashSet;
import java.util.List;
import java.util.Set;

import org.bukkit.command.CommandSender;
import org.bukkit.command.ConsoleCommandSender;
import org.bukkit.command.PluginCommand;
import org.bukkit.entity.Player;
import org.bukkit.plugin.PluginManager;
import org.bukkit.plugin.java.JavaPlugin;

import me.flail.microitems.listeners.ChatListener;
import me.flail.microitems.listeners.UseListener;
import me.flail.microitems.utilities.Config;
import me.flail.microitems.utilities.TabCompleter;
import me.flail.microitems.utilities.Utilities;

public class MicroItems extends JavaPlugin {
	public Utilities utilities = Utilities.get();
	public ConsoleCommandSender console = getServer().getConsoleSender();
	public String version = this.getDescription().getVersion();
	public Config config;
	public PluginManager plugin = getServer().getPluginManager();

	public String chatFormat = "";

	public Set<Player> cooldowns = new HashSet<>();

	@Override
	public void onEnable() {
		long loadTime = System.currentTimeMillis();
		console.sendMessage(utilities.chat(" &eInitializing startup of MicroItems v" + version));

		config = new Config(this).reload();
		console.sendMessage(utilities.chat(" &7Loaded Settings.yml file."));


		registerCommands();
		registerEvents();

		console.sendMessage(utilities.chat(" &aStartup complete. &8(&7" + (System.currentTimeMillis() - loadTime) + "ms&8)\n"));
	}

	@Override
	public void onDisable() {
		getServer().getScheduler().cancelTasks(this);
		console.sendMessage(utilities.chat(" &aShutdown success.  &eGoodbye!"));
	}

	public Config config() {
		config = new Config(this).reload();
		return config;
	}

	private void registerCommands() {
		for (String command : this.getDescription().getCommands().keySet()) {
			PluginCommand cmd = this.getCommand(command);
			List<String> aliases = config.getList("Command.Aliases");
			cmd.setAliases(aliases);

			cmd.setExecutor(this);
			console.sendMessage(utilities.chat(" &7Registered command: &e/" + command));
		}
	}

	private void registerEvents() {
		plugin.registerEvents(new ChatListener(), this);
		console.sendMessage(" Chat Listener is active.");
		plugin.registerEvents(new UseListener(), this);
	}

	@Override
	public boolean onCommand(CommandSender sender, org.bukkit.command.Command command, String label, String[] args) {
		return new Command(sender, command.getName(), args).run();
	}

	@Override
	public List<String> onTabComplete(CommandSender sender, org.bukkit.command.Command command, String label, String[] args) {
		return new TabCompleter(this).construct(command, args);
	}

}
