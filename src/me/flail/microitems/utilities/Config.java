package me.flail.microitems.utilities;

import java.io.File;

import javax.annotation.Nullable;

import org.bukkit.configuration.file.FileConfiguration;

import me.flail.microitems.MicroItems;

public class Config {
	private MicroItems plugin;
	private File file;
	private FileConfiguration config;

	public Config(MicroItems plugin) {
		this.plugin = plugin;
		file = new File(plugin.getDataFolder() + "Settings.yml");
		config = plugin.getConfig();
	}

	public void setValue(String path, Object value) {
		config.set(path, value);
		this.save();
	}


	/**
	 * Gets a value from the config.
	 * 
	 * @param path
	 *                 path in the configuration to get the value from.
	 * @return the value specified by path, null if path was invalid or value wasn't found.
	 */
	@Nullable
	public Object getValue(String path) {
		return config.get(path, null);
	}

	public void reload() {
		plugin.reloadConfig();
		config = plugin.getConfig();
	}

	public void load() {
		if (!file.exists()) {

		}

	}

	protected void save() {

	}

}
