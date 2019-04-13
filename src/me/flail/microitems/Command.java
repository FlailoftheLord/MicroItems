package me.flail.microitems;

import org.bukkit.command.CommandSender;

public class Command {
	private MicroItems plugin = MicroItems.getPlugin(MicroItems.class);
	private CommandSender sender;
	private String command;
	private String[] args;

	public Command(CommandSender sender, String command, String[] args) {
		this.sender = sender;
		this.command = command;
		this.args = args;
	}

	public boolean run() {

		return false;
	}

}
