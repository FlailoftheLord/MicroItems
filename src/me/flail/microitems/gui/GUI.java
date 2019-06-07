package me.flail.microitems.gui;

import java.util.UUID;

import org.bukkit.Bukkit;
import org.bukkit.Material;
import org.bukkit.entity.Player;
import org.bukkit.inventory.Inventory;
import org.bukkit.inventory.ItemStack;

import me.flail.MicroItems;
import me.flail.microitems.utilities.Utilities;

public class GUI extends Utilities {

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
		MicroItems.activeGuis.put(player.getUniqueId(), uuid);
		return this;
	}

	public void close(Player player) {
		MicroItems.activeGuis.remove(player.getUniqueId());
	}

	/*
	 * Start of optional interface.
	 */

	/**
	 * generate the inventory.
	 */
	public void generate() {
	}

	/**
	 * All methods for setings items in the inventory slots should go in here, and be called in the
	 * Gui#generate() function.
	 */
	protected void createContents() {
	}

}
