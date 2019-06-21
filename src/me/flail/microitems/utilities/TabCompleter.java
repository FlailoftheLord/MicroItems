package me.flail.microitems.utilities;

import java.util.ArrayList;

import org.bukkit.Bukkit;
import org.bukkit.command.Command;
import org.bukkit.entity.Player;

import me.flail.MicroItems;

public class TabCompleter extends ArrayList<String> {
	private MicroItems plugin;
	/**
	 * UID
	 */
	private static final long serialVersionUID = 10971234812998374L;

	public TabCompleter(MicroItems plugin) {
		this.plugin = plugin;
	}

	public TabCompleter construct(Command command, String[] args) {
		try {

			String[] arguments = command.getUsage().split(" ");
			for (String line : this.parseArgs(arguments[args.length])) {

				if (line.contains("%")) {
					if ((args.length > 1) && line.split("%")[0].equalsIgnoreCase(args[args.length - 2])) {
						String finalOption = line.split("%")[1];
						if (finalOption.equalsIgnoreCase("placeholder-name")) {
							for (String alias : plugin.settings.getList("Chat.ItemPlaceholders")) {
								this.add(alias);
							}
							continue;
						}

						this.add(finalOption);
					}

					continue;
				}

				this.add(line);
			}
		} catch (Throwable t) {
		}

		for (String finalOption : this.toArray(new String[] {})) {
			if (finalOption.equalsIgnoreCase("item-types")) {
				for (String itemName : Utilities.itemNameList()) {
					this.add(itemName);
				}

				continue;
			}
			if (finalOption.equalsIgnoreCase("online-players")) {
				for (Player p : Bukkit.getOnlinePlayers()) {
					this.add(p.getName());
				}
				this.remove(finalOption);
				continue;
			}
			if (finalOption.equalsIgnoreCase("amount")) {
				for (int n = 64; n > 0; n--) {
					this.add(n + "");
				}
				this.remove(finalOption);
				continue;
			}

		}

		for (String arg : this.toArray(new String[] {})) {
			if (!arg.startsWith(args[args.length - 1])) {
				this.remove(arg);
			}
		}

		return this;
	}



	protected String[] parseArgs(String line) {
		Utilities utilities = new Utilities();

		String[] chars = { "<", ">", "[", "]" };

		return utilities.removeChars(line, chars).split(":");
	}

}
