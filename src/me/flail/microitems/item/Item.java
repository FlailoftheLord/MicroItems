package me.flail.microitems.item;

import java.util.ArrayList;
import java.util.List;

import org.bukkit.inventory.ItemStack;
import org.bukkit.inventory.meta.ItemMeta;
import org.bukkit.plugin.java.JavaPlugin;

import me.flail.MicroItems;
import me.flail.tools.Logger;


/**
 * Allows for Wider control of an {@link ItemStack}'s data.
 */
public class Item extends Logger {
	private MicroItems plugin = JavaPlugin.getPlugin(MicroItems.class);
	private ItemStack item;
	private ItemType itemType;

	public Item(ItemStack item) {
		this.item = item;
		itemType = new ItemType(item.getType());
	}

	public ItemStack item() {
		return item;
	}

	public ItemMeta meta() {
		return item.getItemMeta();
	}

	public ItemStack setMeta(ItemMeta meta) {
		item.setItemMeta(meta);

		return item;
	}

	public ItemType getType() {
		return itemType;
	}

	public String friendlyName() {
		return getType().name();
	}

	public String getName() {
		return meta().hasDisplayName() ? meta().getDisplayName() : getType().name();
	}

	public Item setName(String name) {
		ItemMeta meta = meta();
		meta.setDisplayName(name);

		item = setMeta(meta);
		return this;
	}

	public List<String> getLore() {
		return meta().hasLore() ? meta().getLore() : new ArrayList<>();
	}

	public Item setLore(List<String> lore) {
		ItemMeta meta = meta();
		meta.setLore(lore);

		item = setMeta(meta);
		return this;
	}

	public Item addTag(String tag, String data) {
		item = addTag(item, tag, data);

		return this;
	}

	public Item removeTag(String tag) {
		item = removeTag(item, tag);

		return this;
	}

	public boolean hasTag(String tag) {
		return hasTag(item, tag);
	}

	public String getTag(String tag) {
		return getTag(item, tag);
	}

}
