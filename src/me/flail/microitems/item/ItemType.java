package me.flail.microitems.item;

import org.bukkit.Material;

/**
 * A simple class for wider control of an Item's visual data.
 * 
 * @author FlailoftheLord
 */
public class ItemType {
	private String type;
	private String friendlyName;

	public ItemType(Material type) {
		this.type = type.toString().toLowerCase();

		for (String s : this.type.split("_")) {
			String firstChar = Character.toString(s.charAt(0));

			friendlyName += s.replaceFirst("(?i)" + firstChar, firstChar.toUpperCase());
		}

	}


	/**
	 * Gets the literal {@link Material} for this itemtype.
	 * 
	 * @return the literal value of this <code>ItemType</code>
	 */
	public Material literalName() {
		return Material.valueOf(type.toUpperCase());
	}

	/**
	 * @return A friendly version of this item's name.
	 *         For example, if the Material type is <code>GOLDEN_CARROT</code>, this will return
	 *         <code>GoldenCarrot</code>
	 */
	public String name() {
		return friendlyName;
	}

}
