package me.flail.microitems;

import java.util.HashSet;
import java.util.List;
import java.util.Set;

import org.bukkit.command.Command;
import org.bukkit.command.CommandSender;
import org.bukkit.command.ConsoleCommandSender;
import org.bukkit.command.PluginCommand;
import org.bukkit.entity.Player;
import org.bukkit.event.Listener;
import org.bukkit.plugin.PluginManager;
import org.bukkit.plugin.java.JavaPlugin;

import me.flail.microitems.listeners.ChatListener;
import me.flail.microitems.listeners.UseListener;
import me.flail.microitems.utilities.Config;
import me.flail.microitems.utilities.TabCompleter;
import me.flail.microitems.utilities.Utilities;

public class MicroItems extends JavaPlugin implements Listener {
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
		chatFormat = config.getValue("Chat.DefaultFormat").toString();


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
			try {
				PluginCommand command = getCommand(cmd);
				command.setExecutor(this);

				console.sendMessage(utilities.chat(" &7Registered command: &e/" + cmd));
			} catch (Throwable t) {
				console.sendMessage(utilities.chat(" &cCouldn't register commands!"));
				console.sendMessage(utilities.chat(" &7Please restart the server to use MicroItems commands."));
				t.fillInStackTrace();
				t.printStackTrace();
			}



		}
	}

	private void registerEvents() {
		plugin.registerEvents(new ChatListener(), this);
		console.sendMessage(" Chat Listener is active.");
		plugin.registerEvents(new UseListener(), this);
		plugin.registerEvents(this, this);
	}

	@Override
	public boolean onCommand(CommandSender sender, Command command, String label, String[] args) {
		return new me.flail.microitems.Command(sender, command.getName(), args).run();
	}

	@Override
	public List<String> onTabComplete(CommandSender sender, Command command, String label, String[] args) {
		return new TabCompleter(this).construct(command, args);
	}


}
