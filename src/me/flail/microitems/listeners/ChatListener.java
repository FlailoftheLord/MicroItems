package me.flail.microitems.listeners;

import java.util.List;

import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.EventPriority;
import org.bukkit.event.Listener;
import org.bukkit.event.player.AsyncPlayerChatEvent;
import org.bukkit.plugin.java.JavaPlugin;

import me.flail.MicroItems;
import me.flail.microitems.utilities.Settings;
import me.flail.microitems.utilities.Utilities;

public class ChatListener extends Utilities implements Listener {
	private MicroItems plugin = JavaPlugin.getPlugin(MicroItems.class);
	private Settings config = new Settings(plugin);

	@EventHandler(priority = EventPriority.LOWEST)
	public void playerChat(AsyncPlayerChatEvent event) {
		Player player = event.getPlayer();
		plugin.chatFormat = event.getFormat();
		if (!plugin.chatFormat.equalsIgnoreCase(event.getFormat())) {
			config.setValue("Chat.DefaultFormat", event.getFormat());
		}

		if (player.hasPermission("microitems.chat") && !plugin.cooldowns.contains(player)) {
			if (!event.isCancelled()) {
				List<String> placeholders = config.getList("Chat.ItemPlaceholders");
				for (String p : placeholders) {
					if (event.getMessage().contains(p)) {
						event.setCancelled(
								this.broadcastChatItem(player, p, event.getFormat(), event.getRecipients(), event.getMessage()));
						break;
					}

				}

			}

		}

	}

}
