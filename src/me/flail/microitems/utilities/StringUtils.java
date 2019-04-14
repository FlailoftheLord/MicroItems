package me.flail.microitems.utilities;

import org.bukkit.ChatColor;

public class StringUtils {

	protected String removeChars(String message, String[] chars) {
		String modified = message;
		for (String c : chars) {
			modified = modified.replace(c, "");
		}

		return modified;
	}

	public String chat(String message) {
		return ChatColor.translateAlternateColorCodes('&', message);
	}

}
