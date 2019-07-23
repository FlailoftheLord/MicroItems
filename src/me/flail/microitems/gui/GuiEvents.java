package me.flail.microitems.gui;

import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.entity.EntityDamageByEntityEvent;
import org.bukkit.event.inventory.InventoryClickEvent;
import org.bukkit.event.inventory.InventoryCloseEvent;
import org.bukkit.event.player.AsyncPlayerChatEvent;
import org.bukkit.event.player.PlayerKickEvent;
import org.bukkit.event.player.PlayerQuitEvent;

import me.flail.MicroItems;
import me.flail.microitems.item.Item;
import me.flail.tools.Logger;

public class GuiEvents extends Logger implements Listener {

	@EventHandler
	public void playerQuit(PlayerQuitEvent event) {
		close(event.getPlayer());
	}

	@EventHandler
	public void playerKick(PlayerKickEvent event) {
		close(event.getPlayer());
	}

	@EventHandler
	public void invClose(InventoryCloseEvent event) {
		if (event.getPlayer() instanceof Player) {
			close((Player) event.getPlayer());
		}
	}

	@EventHandler
	public void playerChat(AsyncPlayerChatEvent event) {
		close(event.getPlayer());
	}

	@EventHandler
	public void playerDamage(EntityDamageByEntityEvent event) {
		if (event.getEntity() instanceof Player) {
			close((Player) event.getEntity());
		}

	}

	@EventHandler
	public void invClick(InventoryClickEvent event) {

		if (event.getWhoClicked() instanceof Player) {
			Player clicker = (Player) event.getWhoClicked();
			if (MicroItems.activeGuis.containsKey(clicker.getUniqueId())) {
				event.setCancelled(true);
				Item item = new Item(event.getCurrentItem());

				if (item.hasTag("inv-backups")) {
					close(clicker);
					clicker.sendMessage(chat(""));

					return;
				}

				if (item.hasTag("item-generator")) {

				}

			}

		}

	}

	protected void close(Player player) {
		MicroItems.activeGuis.remove(player.getUniqueId());
		if ((player != null) && player.isValid()) {
			try {
				player.closeInventory();
			} catch (Throwable T) {
			}
		}
	}

}
