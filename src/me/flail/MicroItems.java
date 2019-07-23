package me.flail;

import java.util.HashMap;
import java.util.HashSet;
import java.util.List;
import java.util.Map;
import java.util.Set;
import java.util.UUID;

import org.bukkit.Server;
import org.bukkit.command.Command;
import org.bukkit.command.CommandSender;
import org.bukkit.command.ConsoleCommandSender;
import org.bukkit.command.PluginCommand;
import org.bukkit.entity.Player;
import org.bukkit.event.Listener;
import org.bukkit.plugin.PluginManager;
import org.bukkit.plugin.java.JavaPlugin;

import me.flail.microitems.gui.GuiEvents;
import me.flail.microitems.listeners.ChatListener;
import me.flail.microitems.listeners.SpawnedItemListener;
import me.flail.microitems.listeners.UseListener;
import me.flail.microitems.utilities.Settings;
import me.flail.microitems.utilities.TabCompleter;
import me.flail.microitems.utilities.Utilities;
import me.flail.tools.Logger;

public class MicroItems extends JavaPlugin implements Listener {
	public ConsoleCommandSender console = getServer().getConsoleSender();
	public String version = this.getDescription().getVersion();
	public Settings settings;

	public Server server = getServer();
	public PluginManager plugin = server.getPluginManager();

	public static Map<UUID, UUID> activeGuis = new HashMap<>();

	public String chatFormat = "";

	public Set<Player> cooldowns = new HashSet<>();

	@Override
	public void onLoad() {
		versionCheck();
	}

	@Override
	public void onEnable() {
		Utilities utilities = new Utilities();

		long loadTime = System.currentTimeMillis();
		console.sendMessage(utilities.chat(" &eInitializing startup of MicroItems v" + version));

		settings = settings();
		console.sendMessage(utilities.chat(" &7Loaded Settings.yml file."));
		chatFormat = settings.getValue("Chat.DefaultFormat").toString();

		Utilities.generateItemFile();

		registry();

		console.sendMessage(utilities.chat(" &aStartup complete. &8(&7" + (System.currentTimeMillis() - loadTime) + "ms&8)\n"));
	}

	@Override
	public void onDisable() {
		Utilities utilities = new Utilities();

		getServer().getScheduler().cancelTasks(this);
		console.sendMessage(utilities.chat(" &aShutdown success.  &eGoodbye!"));
	}

	public Settings settings() {
		settings = new Settings(this).reload();
		return settings;
	}

	public void registry() {
		registerCommands();
		registerEventListeners();
	}



	private void registerCommands() {
		Utilities utilities = new Utilities();

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

	private void registerEventListeners() {
		plugin.registerEvents(new ChatListener(), this);
		console.sendMessage(" Chat Listener is active.");
		plugin.registerEvents(new UseListener(), this);
		plugin.registerEvents(this, this);
		plugin.registerEvents(new GuiEvents(), this);
		plugin.registerEvents(new SpawnedItemListener(), this);
	}

	@Override
	public boolean onCommand(CommandSender sender, Command command, String label, String[] args) {
		return new me.flail.microitems.Command(sender, command.getName(), args).run();
	}

	@Override
	public List<String> onTabComplete(CommandSender sender, Command command, String label, String[] args) {
		return new TabCompleter(this).construct(command, args);
	}

	private void versionCheck() {
		if (!server.getVersion().contains("1.14.2")) {
			new Logger().console("&cYOU MUST BE USING &7Spigot version 1.14.2 &cin order for MicroItems to run!");

			this.setEnabled(false);
		}

	}

}
