package me.flail.microitems;

import java.lang.reflect.Field;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

import org.bukkit.Bukkit;
import org.bukkit.command.Command;
import org.bukkit.command.CommandMap;
import org.bukkit.command.CommandSender;
import org.bukkit.command.ConsoleCommandSender;
import org.bukkit.command.PluginCommand;
import org.bukkit.entity.Player;
import org.bukkit.plugin.PluginManager;
import org.bukkit.plugin.SimplePluginManager;
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

	public void registry() {
		registerCommands();
		registerEvents();
	}



	private void registerCommands() {
		for (String cmd : this.getDescription().getCommands().keySet()) {
			List<String> aliases = config.getList("Command.Aliases");
			PluginCommand command = this.getCommand(cmd);
			command.setAliases(aliases);
			command.setExecutor(this);

			serverCommandMap().register(cmd, command);
			console.sendMessage(utilities.chat(" &7Registered command: &e/" + cmd));
		}
	}

	private void registerEvents() {
		plugin.registerEvents(new ChatListener(), this);
		console.sendMessage(" Chat Listener is active.");
		plugin.registerEvents(new UseListener(), this);
	}

	@Override
	public boolean onCommand(CommandSender sender, Command command, String label, String[] args) {
		return new me.flail.microitems.Command(sender, command.getName(), args).run();
	}

	@Override
	public List<String> onTabComplete(CommandSender sender, Command command, String label, String[] args) {
		return new TabCompleter(this).construct(command, args);
	}

	private static CommandMap serverCommandMap() {
		try {
			if (Bukkit.getPluginManager() instanceof SimplePluginManager) {
				Field f = SimplePluginManager.class.getDeclaredField("commandMap");
				f.setAccessible(true);
				CommandMap commandMap = (CommandMap) f.get(Bukkit.getPluginManager());

				return commandMap;
			}
		} catch (Throwable t) {
			t.printStackTrace();
		}

		return null;
	}

}
