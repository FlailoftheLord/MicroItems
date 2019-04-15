package me.flail.microitems.utilities;

import java.io.File;
import java.io.IOException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

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
		file = new File(plugin.getDataFolder() + "/Settings.yml");
		this.reload();
		this.setup();
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

	/**
	 * Will return an empty list if the specified path is invalid.
	 * 
	 * @param path
	 *                 the path in the configuration file to find the list.
	 * @return the list, or empty if path is invalid.
	 */
	public List<String> getList(String path) {
		return config.getStringList(path);
	}

	public Config reload() {
		try {
			this.load();
		} catch (Exception e) {
			plugin.getLogger().warning(
					"Couldn't load settings file! Try Restarting your server."
							+ "\nIf this persists, delete the Settings.yml file in this plugin's data folder; and restart.");
			e.printStackTrace();
		}
		return this;
	}

	protected Config load() throws IOException {
		plugin.getDataFolder().mkdirs();

		if (!file.exists()) {
			file.createNewFile();
		}

		try {
			config = YamlConfiguration.loadConfiguration(file);

			return this;
		} catch (Exception e) {
			throw new IOException("Couldn't load settings file.");
		}
	}

	protected void save() {
		try {
			config.save(file);
		} catch (IOException e) {
			e.printStackTrace();
			plugin.getLogger().warning("Couldn't save settings file!");
		}
	}

	public void setup() {
		header();
		loadValues();
	}

	protected void loadValues() {
		Map<String, Object> values = new HashMap<>();


		String prefix = "&7(&eMitems&7)";
		String reloadMessage = "[prefix] &areloaded settings!";
		List<String> itemPlaceholders = new ArrayList<>();
		itemPlaceholders.add("[item]");
		itemPlaceholders.add("[hand]");
		String itemPrefix = "&e[&b";
		String itemSuffix = "&e]&r";
		String defaultFormat = "%1$s &8»&7 %2$s";

		values.put("Chat.Prefix", prefix);
		values.put("Chat.DefaultFormat", defaultFormat);
		values.put("Chat.ReloadMessage", reloadMessage);
		values.put("Chat.ItemPlaceholders", itemPlaceholders);
		values.put("Item.Prefix", itemPrefix);
		values.put("Item.Suffix", itemSuffix);

		/**
		 * A SUPER DUPER FANCY SEPARATOR!! ooh yeaaaaa ;p
		 */

		for (String path : values.keySet()) {
			if (!config.contains(path)) {
				setValue(path, values.get(path));
			}
		}

	}

	private void header() {
		String headerValue = "#-----------------------------------------------------------------\r\n" +
				"#==================================================================#\r\n" +
				"#                                                                  #\r\n" +
				"#                MicroItems by FlailoftheLord.                     #\r\n" +
				"#         -=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-                  #\r\n" +
				"#           If you have any Questions or feedback                  #\r\n" +
				"#              Join my discord server here:                        #\r\n" +
				"#               https://discord.gg/wuxW5PS                         #\r\n" +
				"#   ______               __        _____                           #\r\n" +
				"#   |       |           /  \\         |        |                    #\r\n" +
				"#   |__     |          /____\\        |        |                    #\r\n" +
				"#   |       |         /      \\       |        |                    #\r\n" +
				"#   |       |_____   /        \\    __|__      |______              #\r\n" +
				"#                                                                  #\r\n" +
				"#==================================================================#\r\n" +
				"#-----------------------------------------------------------------\r\n";
		config.options().header(headerValue);
		this.save();
	}

}
