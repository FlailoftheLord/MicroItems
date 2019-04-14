package me.flail.microitems.listeners;

import java.util.List;

import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.player.AsyncPlayerChatEvent;
import org.bukkit.plugin.java.JavaPlugin;

import me.flail.microitems.MicroItems;
import me.flail.microitems.utilities.Config;

public class ChatListener implements Listener {
	private MicroItems plugin = JavaPlugin.getPlugin(MicroItems.class);
	private Config config = new Config(plugin);

	@EventHandler
	public void playerChat(AsyncPlayerChatEvent event) {
		if (event.isAsynchronous()) {
			Player player = event.getPlayer();
			String message = event.getMessage();
			Object c = config.getValue("Chat.ItemPlaceholders");
			if (c instanceof List) {

			}

		}
	}

}
