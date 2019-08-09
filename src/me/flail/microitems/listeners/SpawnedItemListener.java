package me.flail.microitems.listeners;

import java.util.ArrayList;
import java.util.List;
import java.util.UUID;

import org.bukkit.entity.Entity;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.EventPriority;
import org.bukkit.event.Listener;
import org.bukkit.event.block.BlockDropItemEvent;
import org.bukkit.event.entity.EntityDamageByEntityEvent;
import org.bukkit.event.entity.EntityDeathEvent;
import org.bukkit.event.entity.EntityDropItemEvent;
import org.bukkit.event.entity.EntityPickupItemEvent;
import org.bukkit.inventory.ItemStack;
import org.bukkit.metadata.FixedMetadataValue;

import me.flail.microitems.item.Item;
import me.flail.tools.Logger;

@SuppressWarnings("deprecation")
public class SpawnedItemListener extends Logger implements Listener {

	@EventHandler(priority = EventPriority.MONITOR)
	public void blockDropItem(BlockDropItemEvent event) {

		for (org.bukkit.entity.Item item : event.getItems()) {

			item.setMetadata("MicroItems-dropped-item",
					new FixedMetadataValue(plugin, event.getPlayer().getUniqueId().toString()));
		}

	}

	@EventHandler(priority = EventPriority.MONITOR)
	public void entityDamage(EntityDamageByEntityEvent event) {
		if (event.getDamager() instanceof Player) {
			Entity damaged = event.getEntity();

			if (!damaged.hasMetadata("MicroItems-attacker")) {
				damaged.setMetadata("MicroItems-attacker", new FixedMetadataValue(plugin, ((Player) event.getDamager()).getUniqueId()));

				plugin.server.getScheduler().scheduleSyncDelayedTask(plugin, () -> {
					if (damaged.isValid()) {

						damaged.removeMetadata("MicroItems-attacker", plugin);
					}

				}, 120L);

			}

		}

	}

	@EventHandler(priority = EventPriority.MONITOR)
	public void entityDeath(EntityDeathEvent event) {
		Entity entity = event.getEntity();
		if (!(entity instanceof Player)) {
			List<ItemStack> drops = event.getDrops();

			if (entity.hasMetadata("MicroItems-attacker")) {
				UUID damager = UUID.fromString(entity.getMetadata("MicroItems-attacker").get(0).asString());
				List<ItemStack> newDrops = new ArrayList<>();

				for (ItemStack item : drops.toArray(new ItemStack[] {})) {
					newDrops.add(addTag(item, "dropped-item", damager.toString()));
				}

				event.getDrops().clear();
				event.getDrops().addAll(newDrops);
			}

		}

	}

	@EventHandler(priority = EventPriority.HIGHEST)
	public void itemPickup(EntityPickupItemEvent event) {
		if ((event.getEntity() instanceof Player) && !event.isCancelled()) {

			Player player = (Player) event.getEntity();

			Item item = new Item(event.getItem().getItemStack());
			String tag = null;

			if (item.hasTag("dropped-item")) {
				tag = item.getTag("dropped-item");
			}
			if (event.getItem().hasMetadata("MicroItems-dropped-item")) {
				tag = event.getItem().getMetadata("MicroItems-dropped-item").get(0).asString();
			}

			if ((tag != null) && !UUID.fromString(tag).equals(player.getUniqueId())) {
				event.setCancelled(true);
				return;
			}

			item.removeTag("dropped-item");
		}

	}

	@EventHandler(priority = EventPriority.MONITOR)
	public void itemDrop(EntityDropItemEvent event) {
		if ((event.getEntity() instanceof Player) && !event.isCancelled()) {
			boolean showDroppedItemName = plugin.settings.file().getBoolean("Item.ShowDroppedItemName");
			String format = plugin.settings.file().getValue("Item.DroppedItemNameFormat").replaceAll("\\[item\\]",
					this.name(event.getItemDrop().getItemStack().getType()));

			org.bukkit.entity.Item item = event.getItemDrop();

			item.setCustomName(chat(format));
			item.setCustomNameVisible(showDroppedItemName);

		}

	}

}
