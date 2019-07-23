package me.flail.microitems.utilities;

import org.bukkit.ChatColor;
import org.bukkit.plugin.java.JavaPlugin;

import me.flail.MicroItems;

public class StringUtils {
	protected MicroItems plugin = JavaPlugin.getPlugin(MicroItems.class);

	protected String removeChars(String message, String[] chars) {
		String modified = message;
		for (String c : chars) {
			modified = modified.replace(c, "");
		}

		return modified;
	}

	public String chat(String message) {
		String prefix = new Settings(JavaPlugin.getPlugin(MicroItems.class)).getValue("Chat.Prefix").toString();
		return ChatColor.translateAlternateColorCodes('&', message.replace("[prefix]", prefix));
	}

}
