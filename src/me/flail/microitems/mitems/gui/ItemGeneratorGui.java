package me.flail.microitems.mitems.gui;

import java.util.ArrayList;
import java.util.List;

import org.bukkit.Material;
import org.bukkit.inventory.ItemStack;

import me.flail.microitems.gui.GUI;
import me.flail.microitems.item.Item;

public class ItemGeneratorGui extends GUI {

	public ItemGeneratorGui(String name) {
		super(name);
	}

	@Override
	public void generate() {
		createContents();

		for (Integer i : contents.keySet()) {
			this.setItem(i.intValue(), contents.get(i).item());

		}

		this.fillEmptySpace(new ItemStack(Material.WHITE_STAINED_GLASS_PANE));
	}

	@Override
	public void createContents() {
		Item mainItem = new Item(new ItemStack(Material.BARRIER)).addTag("item", " ");
		Item name = new Item(new ItemStack(Material.ANVIL)).addTag("item-name", " ");
		Item type = new Item(new ItemStack(Material.LIME_DYE)).addTag("item-type", " ");
		Item enchants = new Item(new ItemStack(Material.ENCHANTING_TABLE)).addTag("item-enchant", " ");
		Item attributes = new Item(new ItemStack(Material.COMPOSTER)).addTag("item-attribute", " ");
		Item actions = new Item(new ItemStack(Material.STONE_AXE)).addTag("item-action", " ");

		List<String> lore = new ArrayList<>();

	}

}
