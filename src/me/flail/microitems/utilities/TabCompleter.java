package me.flail.microitems.utilities;

import java.util.ArrayList;

import org.bukkit.command.Command;

import me.flail.microitems.MicroItems;

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
				if (line.startsWith(args[args.length - 1])
						|| (line.contains("%") && line.split("%")[1].startsWith(args[args.length - 1]))) {
					if (line.contains("%")) {
						if ((args.length > 1) && line.split("%")[0].equalsIgnoreCase(args[args.length - 2])) {
							String finalOption = line.split("%")[1];
							if (finalOption.equalsIgnoreCase("placeholder-name")) {
								for (String alias : plugin.config.getList("Chat.ItemPlaceholders")) {
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

			}
		} catch (Throwable t) {
		}

		return this;
	}



	protected String[] parseArgs(String line) {
		String[] chars = { "<", ">", "[", "]" };

		return plugin.utilities.removeChars(line, chars).split(":");
	}

}
