package me.flail.microitems.utilities;

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
}
