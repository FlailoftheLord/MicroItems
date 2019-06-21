package me.flail.microitems.utilities;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.annotation.Nullable;

import org.bukkit.configuration.file.FileConfiguration;
import org.bukkit.configuration.file.YamlConfiguration;

import me.flail.MicroItems;
import me.flail.tools.DataFile;

public class Settings {
	private MicroItems plugin;
	private DataFile file;

	private FileConfiguration config = new YamlConfiguration();

	public Settings(MicroItems plugin) {
		this.plugin = plugin;
		file = new DataFile("Settings.yml");
		this.reload();
		this.setup();
	}

	public void setValue(String path, Object value) {
		file.setValue(path, value);
	}

	public DataFile file() {
		return file;
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
		return file.getObj(path);
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

	public Settings reload() {
		try {
			file.load();
		} catch (Exception e) {
			plugin.getLogger().warning(
					"Couldn't load settings file! Try Restarting your server."
							+ "\nIf this persists, delete the Settings.yml file in this plugin's data folder; and restart.");
			e.printStackTrace();
		}
		return this;
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
		values.put("Item.DefaultStackSize", Integer.valueOf(1));

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
		file.setHeader(headerValue);
	}

}
