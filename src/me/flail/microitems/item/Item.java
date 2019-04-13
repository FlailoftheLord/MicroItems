package me.flail.microitems.item;

import org.bukkit.inventory.ItemStack;


/**
 * Allows for Wider control of an {@link ItemStack}'s data.
 * 
 * @author FlailoftheLord
 */
public class Item {
	private ItemStack item;
	private ItemType itemType;

	public Item(ItemStack item) {
		this.item = item;
		itemType = new ItemType(item.getType());
	}

	public ItemStack item() {
		return item;
	}

	public ItemType getType() {
		return itemType;
	}

	public String friendlyName() {
		return itemType.name();
	}

}
