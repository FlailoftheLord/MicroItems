package me.flail.microitems.utilities;

import org.bukkit.Material;
import org.bukkit.NamespacedKey;
import org.bukkit.inventory.ItemStack;
import org.bukkit.inventory.meta.ItemMeta;
import org.bukkit.persistence.PersistentDataType;

import me.flail.microitems.item.ItemType;
import net.md_5.bungee.api.chat.BaseComponent;
import net.md_5.bungee.api.chat.ComponentBuilder;
import net.md_5.bungee.api.chat.HoverEvent;
import net.md_5.bungee.api.chat.TextComponent;

public class ItemUtils extends StringUtils {

	protected String name(Material material) {
		return new ItemType(material).name();
	}

	protected BaseComponent[] buildMessage(BaseComponent main, String addons) {
		return new ComponentBuilder(main).append(addons).create();
	}

	protected TextComponent buildChatItem(ItemStack item) {
		TextComponent component = new TextComponent(name(item.getType()));

		if (item.hasItemMeta() && item.getItemMeta().hasDisplayName()) {
			component.setText(item.getItemMeta().getDisplayName());
		}

		BaseComponent[] base = new ComponentBuilder(new TextComponent(new NMSitem().get(item))).create();
		component.setHoverEvent(new HoverEvent(HoverEvent.Action.SHOW_ITEM, base));

		return component;
	}

	protected ItemStack addTag(ItemStack item, String key, String tag) {
		ItemMeta meta = item.getItemMeta();
		NamespacedKey nkey = new NamespacedKey(plugin, "MicroItems-" + key);

		meta.getPersistentDataContainer().set(nkey, PersistentDataType.STRING, tag);

		item.setItemMeta(meta);
		return item;
	}

	protected ItemStack removeTag(ItemStack item, String key) {
		ItemMeta meta = item.getItemMeta();
		NamespacedKey nkey = new NamespacedKey(plugin, "MicroItems-" + key);

		meta.getPersistentDataContainer().remove(nkey);

		item.setItemMeta(meta);
		return item;
	}

	protected String getTag(ItemStack item, String key) {
		ItemMeta meta = item.getItemMeta();
		NamespacedKey nkey = new NamespacedKey(plugin, "MicroItems-" + key);

		if (hasTag(item, key)) {
			return meta.getPersistentDataContainer().get(nkey, PersistentDataType.STRING);
		}

		return "null";
	}

	protected boolean hasTag(ItemStack item, String key) {
		if ((item != null) && item.hasItemMeta()) {

			ItemMeta meta = item.getItemMeta();
			NamespacedKey nkey = new NamespacedKey(plugin, "MicroItems-" + key);

			return meta.getPersistentDataContainer().has(nkey, PersistentDataType.STRING) ? true : false;
		}
		return false;
	}

}
