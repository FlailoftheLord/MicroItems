package me.flail.microitems.gui;

import java.util.Map;
import java.util.UUID;

import org.bukkit.Bukkit;
import org.bukkit.Material;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.entity.EntityDamageEvent;
import org.bukkit.event.inventory.InventoryCloseEvent;
import org.bukkit.event.inventory.InventoryInteractEvent;
import org.bukkit.event.player.AsyncPlayerChatEvent;
import org.bukkit.event.player.PlayerKickEvent;
import org.bukkit.event.player.PlayerQuitEvent;
import org.bukkit.inventory.Inventory;
import org.bukkit.inventory.ItemStack;

import me.flail.microitems.MicroItems;
import me.flail.microitems.utilities.Utilities;

public class GUI extends Utilities {
	private Map<UUID, UUID> activeGuis = MicroItems.activeGuis;

	private UUID uuid;
	private Inventory ui;
	private String name;
	private int size;
	private GuiType type;

	public enum GuiType {
		PLAIN,
		ANVIL,
		HOPPER,
		DISPENSER
	}

	public GUI(String name) {
		this.create(name, 45, GuiType.PLAIN);
	}

	public GUI(String name, int size) {
		this.create(name, size, GuiType.PLAIN);
	}

	public GUI(String name, int size, GuiType type) {
		this.create(name, size, type);
	}

	public String getName() {
		return name;
	}

	public int getSize() {
		return size;
	}

	public GuiType getType() {
		return type;
	}

	public UUID id() {
		return uuid;
	}

	protected void create(String name, int size, GuiType type) {
		this.name = name;
		this.size = size;
		this.type = type;
		uuid = UUID.randomUUID();
		ui = Bukkit.createInventory(null, size, name);
	}

	protected void setItem(int slot, ItemStack item) {
		ui.setItem(slot, item);
	}

	protected void fillEmptySpace(ItemStack item) {
		for (int i = ui.firstEmpty(); i < ui.getSize(); i++) {
			if ((ui.getItem(i) == null) || (ui.getItem(i).getType() == Material.AIR)) {
				ui.setItem(i, item);
			}
		}
	}

	public GUI open(Player player) {
		player.openInventory(ui);
		activeGuis.put(player.getUniqueId(), uuid);
		return this;
	}

	public void close(Player player) {
		activeGuis.remove(player.getUniqueId());
	}

	public class listeners implements Listener {

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
		public void playerDamage(EntityDamageEvent event) {
			if (event.getEntity() instanceof Player) {
				close((Player) event.getEntity());
			}

		}

		@EventHandler
		public void invClick(InventoryInteractEvent event) {

			if (event.getWhoClicked() instanceof Player) {
				Player clicker = (Player) event.getWhoClicked();
				event.setCancelled(activeGuis.containsKey(clicker.getUniqueId()));

			}

		}

	}

	/**
	 * Start of optional interface.
	 */

	public void generate() {
	}

	protected void createContents() {
	}

}
