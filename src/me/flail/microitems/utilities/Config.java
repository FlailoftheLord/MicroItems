package me.flail.microitems.utilities;

import org.bukkit.configuration.file.FileConfiguration;

import me.flail.microitems.MicroItems;

public class Config {
	private MicroItems plugin;
	private FileConfiguration config;

	public Config(MicroItems plugin) {
		this.plugin = plugin;
		config = plugin.getConfig();
	}

	public void setValue(String path, Object value) {

	}

	public void reload() {
		plugin.reloadConfig();
		config = plugin.getConfig();
	}

	public void load() {
		plugin.saveDefaultConfig();
	}

}
