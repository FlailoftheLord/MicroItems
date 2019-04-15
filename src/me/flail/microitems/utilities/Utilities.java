package me.flail.microitems.utilities;

import java.util.Set;

import org.bukkit.Material;
import org.bukkit.entity.Player;
import org.bukkit.inventory.ItemStack;

import me.flail.microitems.MicroItems;

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

	protected boolean broadcastChatItem(Player sender, String placeholder, String format, Set<Player> recipients) {
		ItemStack item = sender.getInventory().getItemInMainHand();
		if ((item == null) || item.getType().equals(Material.AIR)) {
			item = sender.getInventory().getItemInOffHand();
			if ((item == null) || item.getType().equals(Material.AIR)) {
				sender.sendMessage(chat("&cYou must hold an item in your hand!"));
				return true;
			}

		}

		Config config = new Config(MicroItems.getPlugin(MicroItems.class));
		String itemFormat = config.getValue("Item.Format").toString();

		return false;
	}
}
