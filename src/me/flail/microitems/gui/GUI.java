package me.flail.microitems.gui;

import java.util.HashMap;
import java.util.Map;
import java.util.UUID;

import org.bukkit.Bukkit;
import org.bukkit.Material;
import org.bukkit.entity.Player;
import org.bukkit.inventory.Inventory;
import org.bukkit.inventory.ItemStack;
import org.bukkit.inventory.meta.ItemMeta;

import me.flail.MicroItems;
import me.flail.microitems.item.Item;
import me.flail.microitems.utilities.Utilities;

/**
 * All extends of this must override {@link #generate()} and {@link #createContents()}
 * 
 * @author FlailoftheLord
 */
public class GUI extends Utilities {
	protected Map<Integer, Item> contents = new HashMap<>();

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
		ItemMeta meta = item.getItemMeta();
		meta.setDisplayName(" ");
		item.setItemMeta(meta);

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
	 * Start of interface.
	 */

	/**
	 * Generate the inventory from the Item Map.
	 * This method must call {@link #createContents()} to generate the Item Map.
	 * You can also call {@link #fillEmptySpace(ItemStack)} to fill up the empty space with an item.
	 */
	public void generate() {
		createContents();
	}

	/**
	 * All methods for setings items in the inventory slots should go in here, and be called in the
	 * {@link #generate()} function.
	 */
	protected void createContents() {
	}

}
