package me.flail.microitems.utilities;

import java.util.Set;

import net.md_5.bungee.api.chat.TextComponent;

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

	protected void broadcastChatItem(TextComponent item, String format, Set<Player> recipients) {

	}
}
