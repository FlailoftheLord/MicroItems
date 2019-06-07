package me.flail.microitems.item;

import java.util.ArrayList;
import java.util.List;

import org.bukkit.NamespacedKey;
import org.bukkit.inventory.ItemStack;
import org.bukkit.inventory.meta.ItemMeta;
import org.bukkit.inventory.meta.tags.ItemTagType;
import org.bukkit.persistence.PersistentDataType;
import org.bukkit.plugin.java.JavaPlugin;

import me.flail.MicroItems;


/**
 * Allows for Wider control of an {@link ItemStack}'s data.
 * 
 * @author FlailoftheLord
 */
@SuppressWarnings("deprecation")
public class Item {
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

	public ItemType getType() {
		return itemType;
	}

	public String friendlyName() {
		return getType().name();
	}

	public String getName() {
		return item().getItemMeta().hasDisplayName() ? item().getItemMeta().getDisplayName() : getType().name();
	}

	public void setName(String name) {
		item().getItemMeta().setDisplayName(name);
	}

	public List<String> getLore() {
		return item().getItemMeta().hasLore() ? item().getItemMeta().getLore() : new ArrayList<>();
	}

	public void setLore(List<String> lore) {
		item().getItemMeta().setLore(lore);
	}

	public Item addNBT(String data) {
		if (plugin.server.getVersion().contains("1.13")) {
			item().getItemMeta().getCustomTagContainer().setCustomTag(new NamespacedKey(plugin, "mitems"), ItemTagType.STRING, data);
			return this;
		}

		item().getItemMeta().getPersistentDataContainer().set(new NamespacedKey(plugin, "mitems"),
				PersistentDataType.STRING, data);
		return this;
	}

	public ItemMeta getMeta() {
		return item().getItemMeta();
	}

}
