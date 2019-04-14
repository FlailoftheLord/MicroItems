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
		String[] arguments = command.getUsage().split(" ");
		for (String line : this.parseArgs(arguments[args.length - 1])) {
			if (line.startsWith(args[args.length - 1])) {
				if (line.contains("%") && (args.length > 1) && line.split("%")[0].equalsIgnoreCase(args[1])) {
					this.add(line);
					return this;
				}
				this.add(line);
			}
		}

		return this;
	}

	protected String[] parseArgs(String line) {
		String[] chars = { "<", ">", "[", "]" };

		return plugin.utilities.removeChars(line, chars).split(":");
	}

}
