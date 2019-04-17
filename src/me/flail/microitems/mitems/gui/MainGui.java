package me.flail.microitems.mitems.gui;

import java.util.HashMap;
import java.util.Map;

import org.bukkit.Material;
import org.bukkit.inventory.ItemStack;

import me.flail.microitems.gui.GUI;
import me.flail.microitems.item.Item;

public class MainGui extends GUI {
	private Map<Integer, Item> contents = new HashMap<>();

	public MainGui(String name) {
		super(name);
		createContents();
		generate();
	}

	@Override
	public void generate() {
		for (Integer i : contents.keySet()) {
			this.setItem(i.intValue(), contents.get(i).item());
		}

		this.fillEmptySpace(new ItemStack(Material.GRAY_STAINED_GLASS_PANE));
	}

	@Override
	protected void createContents() {
		Item backups = new Item(new ItemStack(Material.ENDER_CHEST));
		Item itemGen = new Item(new ItemStack(Material.ANVIL));

		backups.setName(chat("&eInventory Backups"));
		itemGen.setName(chat("&aCustom Item Generator"));

		contents.put(Integer.valueOf(11), backups);
		contents.put(Integer.valueOf(13), itemGen);
	}

}
