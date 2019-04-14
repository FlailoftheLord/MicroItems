package me.flail.microitems.utilities;

import org.bukkit.Material;
import org.bukkit.inventory.ItemStack;

import me.flail.microitems.item.ItemType;
import me.flail.microitems.utilities.nms.NMSitem;
import net.md_5.bungee.api.chat.BaseComponent;
import net.md_5.bungee.api.chat.ComponentBuilder;
import net.md_5.bungee.api.chat.HoverEvent;
import net.md_5.bungee.api.chat.TextComponent;

public class ItemUtils extends StringUtils {

	protected String name(Material material) {
		return new ItemType(material).name();
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

}
