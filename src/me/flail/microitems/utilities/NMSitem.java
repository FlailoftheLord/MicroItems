package me.flail.microitems.utilities;

import org.bukkit.craftbukkit.v1_13_R2.inventory.CraftItemStack;

import net.minecraft.server.v1_13_R2.ItemStack;
import net.minecraft.server.v1_13_R2.NBTTagCompound;

public class NMSitem {

	public String get(org.bukkit.inventory.ItemStack item) {
		NBTTagCompound compound = new NBTTagCompound();

		ItemStack nmsItem = CraftItemStack.asNMSCopy(item);
		compound = nmsItem.save(compound);

		return compound.toString();
	}

}
