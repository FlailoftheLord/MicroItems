package me.flail.microitems.utilities;

import org.bukkit.inventory.ItemStack;

import me.flail.tools.Logger;

public class NMSitem extends Logger {

	public String get(ItemStack item) {

		return versionTranslator(item);
	}

	protected String versionTranslator(ItemStack item) {
		String version = plugin.server.getVersion().replace(".", "_");

		if (!version.contains("1_14_2")) {
			console("&cInvalid Server version&8:&7 " + version + " You must be using &eSpigot v1.14.2");

			return "null";
		}

		net.minecraft.server.v1_14_R1.NBTTagCompound compound = new net.minecraft.server.v1_14_R1.NBTTagCompound();
		net.minecraft.server.v1_14_R1.ItemStack nmsItem = org.bukkit.craftbukkit.v1_14_R1.inventory.CraftItemStack
				.asNMSCopy(item);

		compound = nmsItem.save(compound);
		return compound.toString();
	}

}
