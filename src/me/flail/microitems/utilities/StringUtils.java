package me.flail.microitems.utilities;

import org.bukkit.ChatColor;
import org.bukkit.plugin.java.JavaPlugin;

import me.flail.MicroItems;

public class StringUtils {
	protected MicroItems plugin = JavaPlugin.getPlugin(MicroItems.class);

	public String chat(String message) {
		message = message.toString();

		return ChatColor.translateAlternateColorCodes('&',
				message.replace("[prefix]", plugin.settings.file().getValue("Chat.Prefix")));
	}

	protected String removeChars(String message, String[] chars) {
		String modified = message;
		for (String c : chars) {
			modified = modified.replace(c, "");
		}

		return modified;
	}

}
