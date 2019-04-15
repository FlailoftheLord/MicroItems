package me.flail.microitems.utilities;

import java.util.Set;

import org.bukkit.Material;
import org.bukkit.entity.Player;
import org.bukkit.event.player.AsyncPlayerChatEvent;
import org.bukkit.inventory.ItemStack;

import me.flail.microitems.MicroItems;
import net.md_5.bungee.api.chat.BaseComponent;
import net.md_5.bungee.api.chat.ComponentBuilder;

/**
 * Provides access to Utilities.
 * Do not use the static initializer, instead extend this class or pull it from your main class.
 * 
 * @author FlailoftheLord
 */
public class Utilities extends ItemUtils {
	/**
	 * ONLY to be used in main class, and then, only when entirely neccessary.
	 */
	public static Utilities get() {
		return new Utilities();
	}

	protected boolean broadcastChatItem(Player sender, String placeholder, String format, Set<Player> recipients, String message) {
		MicroItems plugin = MicroItems.getPlugin(MicroItems.class);
		Config config = plugin.config;

		ItemStack item = sender.getInventory().getItemInMainHand();
		if ((item == null) || (item.getType() == Material.AIR)) {
			item = sender.getInventory().getItemInOffHand();
			if ((item == null) || (item.getType() == Material.AIR)) {
				sender.sendMessage(chat("&cYou must hold an item in your hand!"));
				return true;
			}

		}

		String iprefix = chat(config.getValue("Item.Prefix").toString());
		String isuffix = chat(config.getValue("Item.Suffix").toString());


		message = String.format(format, new Object[] { sender.getDisplayName(), message });

		String[] fMessage = message.toString().split(placeholder.replace("[", "\\[").replace("]", "\\]"));
		ComponentBuilder builder = new ComponentBuilder("").append(fMessage[0] + iprefix).append(this.buildChatItem(item))
				.append(isuffix);
		if (fMessage.length > 1) {
			for (int index = 1; index <= fMessage.length; index++) {
				try {
					builder = builder.append(fMessage[index]);
				} catch (Throwable t) {
				}
			}
		}

		BaseComponent[] textComponent = builder.create();
		for (Player p : recipients) {
			p.spigot().sendMessage(textComponent);
		}

		plugin.cooldowns.add(sender);

		AsyncPlayerChatEvent chatEvent = new AsyncPlayerChatEvent(true, sender, message, recipients);

		plugin.getServer().getPluginManager().callEvent(chatEvent);

		plugin.getServer().getScheduler().scheduleSyncDelayedTask(plugin, () -> {
			plugin.cooldowns.remove(sender);
		}, 32L);

		return true;
	}
}
