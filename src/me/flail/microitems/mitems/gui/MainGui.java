package me.flail.microitems.mitems.gui;

import org.bukkit.Material;
import org.bukkit.inventory.ItemStack;

import me.flail.microitems.gui.GUI;
import me.flail.microitems.item.Item;

public class MainGui extends GUI {

	public MainGui(String name) {
		super(name);
		generate();
	}

	@Override
	public void generate() {
		createContents();

		for (Integer i : contents.keySet()) {
			this.setItem(i.intValue(), contents.get(i).item());
		}

		this.fillEmptySpace(new ItemStack(Material.GRAY_STAINED_GLASS_PANE));
	}

	@Override
	protected void createContents() {
		Item backups = new Item(new ItemStack(Material.ENDER_CHEST)).addNBT("inv-backups");
		Item itemGen = new Item(new ItemStack(Material.ANVIL)).addNBT("item-generator");

		backups.setName(chat("&eInventory Backups"));
		itemGen.setName(chat("&aCustom Item Generator"));

		contents.put(Integer.valueOf(11), backups);
		contents.put(Integer.valueOf(13), itemGen);
	}

}
