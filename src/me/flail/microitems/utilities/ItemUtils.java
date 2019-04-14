package me.flail.microitems.utilities;

import org.bukkit.Material;

import me.flail.microitems.item.ItemType;

public class ItemUtils extends StringUtils {

	protected String name(Material material) {
		return new ItemType(material).name();
	}

}
