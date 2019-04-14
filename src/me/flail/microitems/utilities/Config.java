package me.flail.microitems.utilities;

import java.io.File;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

import javax.annotation.Nullable;

import org.bukkit.configuration.file.FileConfiguration;
import org.bukkit.configuration.file.YamlConfiguration;

import me.flail.microitems.MicroItems;

public class Config {
	private MicroItems plugin;
	private File file;
	private FileConfiguration config = new YamlConfiguration();

	public Config(MicroItems plugin) {
		this.plugin = plugin;
		file = new File(plugin.getDataFolder(), "Settings.yml");
		this.reload();
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
		return config.get(path, "");
	}

	public Config reload() {
		try {
			config.load(file);
		} catch (Exception e) {
			e.printStackTrace();
		}
		return this;
	}

	public void load() throws IOException {
		if (!file.exists()) {
			file.createNewFile();
		}

		config = YamlConfiguration.loadConfiguration(file);

		try {
			config.save(file);
		} catch (IOException e) {
			throw new IOException("Couldn't load settings file.");
		}
	}

	protected void save() {
		try {
			this.load();
		} catch (IOException e) {
			e.printStackTrace();
			plugin.getLogger().warning("Couldn't save settings file!");
		}
	}

	public void setup() {
		loadValues();
	}

	protected void loadValues() {
		String prefix = "&7(&eMitems&7)";
		String reloadMessage = "[prefix] &areloaded settings!";
		List<String> itemPlaceholders = new ArrayList<>();
		itemPlaceholders.add("[item]");
		itemPlaceholders.add("[hand]");

		/**
		 * A SUPER DUPER FANCY SEPARATOR!! ooh yeaaaaa ;p
		 */

		config.set("Chat.Prefix", prefix);
		config.set("Chat.ReloadMessage", reloadMessage);
		config.set("Chat.ItemPlaceholders", itemPlaceholders);
		this.save();
	}

}
