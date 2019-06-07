package me.flail.microitems.utilities;

import org.bukkit.ChatColor;

import me.flail.MicroItems;

public class StringUtils {

	protected String removeChars(String message, String[] chars) {
		String modified = message;
		for (String c : chars) {
			modified = modified.replace(c, "");
		}

		return modified;
	}

	public String chat(String message) {
		String prefix = new Config(MicroItems.getPlugin(MicroItems.class)).getValue("Chat.Prefix").toString();
		return ChatColor.translateAlternateColorCodes('&', message.replace("[prefix]", prefix));
	}

}
