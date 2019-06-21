package me.flail.microitems.utilities;

import java.util.ArrayList;
import java.util.List;
import java.util.Set;

import org.bukkit.Material;
import org.bukkit.entity.Player;
import org.bukkit.event.player.AsyncPlayerChatEvent;
import org.bukkit.inventory.ItemStack;
import org.bukkit.plugin.java.JavaPlugin;

import me.flail.MicroItems;
import me.flail.microitems.item.ItemType;
import me.flail.tools.DataFile;
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
		MicroItems plugin = JavaPlugin.getPlugin(MicroItems.class);
		DataFile settings = plugin.settings.file();

		ItemStack item = sender.getInventory().getItemInMainHand();
		if ((item == null) || (item.getType() == Material.AIR)) {
			item = sender.getInventory().getItemInOffHand();
			if ((item == null) || (item.getType() == Material.AIR)) {
				sender.sendMessage(chat("&cYou must hold an item in your hand!"));
				return true;
			}

		}

		String iprefix = chat(settings.getValue("Item.Prefix"));
		String isuffix = chat(settings.getValue("Item.Suffix"));


		message = String.format(format, new Object[] { sender.getDisplayName(), message });

		String[] fMessage = chat(message.toString()).split(placeholder.replace("[", "\\[").replace("]", "\\]"));
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

		AsyncPlayerChatEvent chatEvent = new AsyncPlayerChatEvent(false, sender, chat(message), recipients);

		if (!plugin.getServer().getPluginManager().isPluginEnabled("ChatControl")
				&& !plugin.getServer().getPluginManager().isPluginEnabled("ChatControlPro")) {
			chatEvent.setFormat(plugin.chatFormat);
			plugin.getServer().getPluginManager().callEvent(chatEvent);
		}

		plugin.getServer().getScheduler().scheduleSyncDelayedTask(plugin, () -> {
			plugin.cooldowns.remove(sender);
		}, 32L);

		return true;
	}

	public static DataFile generateItemFile() {
		DataFile file = new DataFile("items.yml");
		if (file.hasValue("Items")) {
			return file;
		}

		List<String> items = new ArrayList<>();

		for (Material m : Material.values()) {
			String type = m.toString().toLowerCase();
			String plainName = type.replace("_", "");
			String name = new ItemType(m).name();

			String value = type + ":" + plainName + ":" + name;
			items.add(value);
		}

		return file.setValue("Items", items);
	}

	public static List<String> itemList() {
		DataFile file = generateItemFile();

		return file.getList("Items");
	}

	public static List<String> itemNameList() {
		List<String> list = new ArrayList<>();

		for (String item : itemList()) {
			for (String name : item.split(":")) {
				list.add(name);
			}
		}

		return list;
	}

	public static ItemStack itemFromName(String name) {
		Material type = Material.AIR;
		for (String item : itemList()) {
			if (item.contains(name.toLowerCase())) {
				type = Material.matchMaterial(item.split(":")[0].toUpperCase());
				break;
			}

		}

		return new ItemStack(type);
	}

}
