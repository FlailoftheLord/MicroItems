package me.flail.microitems.listeners;

import java.util.UUID;

import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.EventPriority;
import org.bukkit.event.Listener;
import org.bukkit.event.block.BlockDropItemEvent;
import org.bukkit.event.entity.EntityPickupItemEvent;

import me.flail.microitems.item.Item;
import me.flail.tools.Logger;

@SuppressWarnings("deprecation")
public class SpawnedItemListener extends Logger implements Listener {

	@EventHandler(priority = EventPriority.MONITOR)
	public void blockDropItem(BlockDropItemEvent event) {
		for (org.bukkit.entity.Item itemStack : event.getItems()) {
			Item item = new Item(itemStack.getItemStack());

			item.addTag("dropped-item", event.getPlayer().getUniqueId().toString());
		}

	}

	@EventHandler(priority = EventPriority.MONITOR)
	public void itemPickup(EntityPickupItemEvent event) {
		if (event.getEntity() instanceof Player) {
			Player player = (Player) event.getEntity();

			Item item = new Item(event.getItem().getItemStack());
			if (item.hasTag("dropped-item")) {
				if (!UUID.fromString(item.getTag("dropped-item")).equals(player.getUniqueId())) {
					event.getItem().setPickupDelay(1);
				}

			}

		}

	}

}
