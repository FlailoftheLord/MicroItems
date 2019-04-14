package me.flail.microitems.listeners;

import java.util.List;
import java.util.regex.Pattern;

import org.bukkit.Material;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.player.AsyncPlayerChatEvent;
import org.bukkit.inventory.ItemStack;
import org.bukkit.plugin.java.JavaPlugin;

import me.flail.microitems.MicroItems;
import me.flail.microitems.utilities.Config;
import me.flail.microitems.utilities.Utilities;

public class ChatListener extends Utilities implements Listener {
	private MicroItems plugin = JavaPlugin.getPlugin(MicroItems.class);
	private Config config = new Config(plugin);

	@EventHandler
	public void playerChat(AsyncPlayerChatEvent event) {
		if (event.isAsynchronous()) {
			Player player = event.getPlayer();
			String message = event.getMessage();
			List<?> c = (List<?>) config.getValue("Chat.ItemPlaceholders");
			for (Object o : c) {
				event.setMessage(message.replace(Pattern.quote("(?i)") + o.toString(), this.getItem(player)));
			}

		}

	}

	private String getItem(Player player) {
		ItemStack item = player.getInventory().getItemInMainHand();
		if ((item == null) || (item.getType() == Material.AIR)) {
			item = player.getInventory().getItemInOffHand();
		}
		if (!item.hasItemMeta() && !item.getItemMeta().hasDisplayName()) {
			return this.name(item.getType());
		}
		return item.getItemMeta().getDisplayName();
	}

}
