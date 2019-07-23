package me.flail.tools;

import java.util.Map;
import java.util.regex.Pattern;

import org.bukkit.ChatColor;

import me.flail.microitems.utilities.Utilities;

public class Tools extends Utilities {


	@Override
	public String chat(String message) {
		message = message.toString();

		return ChatColor.translateAlternateColorCodes('&',
				message.replace("[prefix]", plugin.settings.file().getValue("Chat.Prefix")));
	}

	/**
	 * Converts a string, by translating the following placeholders with their counterparts defined in
	 * the provided Map of placeholders.
	 * 
	 * @param message
	 * @param placeholders
	 *                         Formatted as
	 *                         <code>{@literal Map<String placeholder, String value>}</code>
	 * @return the new String.
	 */
	public String placeholders(String message, Map<String, String> placeholders) {
		if (!placeholders.isEmpty() && (message != null)) {
			for (String p : placeholders.keySet()) {
				if (p != null) {
					message = message.replace(p, placeholders.get(p));
				}
			}
		}
		return chat(message);
	}

	public boolean msgCheck(String message, String text, String type) {
		message = message.toLowerCase();

		switch (type.toLowerCase()) {
		case "starts":
			return message.startsWith(text.toLowerCase());
		case "ends":
			return message.endsWith(text.toLowerCase());
		case "contains":
			return message.contains(text.toLowerCase());
		default:
			return false;

		}
	}

	public String replaceText(String message, String text, String replacement) {
		return message = message.replaceAll("(?i)" + Pattern.quote(text), replacement);
	}

	public String convertArray(String[] values, int start) {
		StringBuilder builder = new StringBuilder();
		while (start < values.length) {
			builder.append(values[start] + " ");

			start++;
		}

		return builder.toString();
	}

}
