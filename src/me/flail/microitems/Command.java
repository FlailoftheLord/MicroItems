package me.flail.microitems;

import java.util.HashSet;
import java.util.List;
import java.util.Set;

import org.bukkit.Bukkit;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;
import org.bukkit.inventory.ItemStack;

import me.flail.MicroItems;
import me.flail.microitems.item.ItemType;
import me.flail.microitems.mitems.gui.MainGui;
import me.flail.microitems.utilities.Settings;
import me.flail.microitems.utilities.Utilities;
import me.flail.tools.Logger;

/**
 * TODO Gotta work on this...
 * Main Command handler class.
 * 
 * @author FlailoftheLord
 */
public class Command extends Logger {
	private MicroItems plugin = MicroItems.getPlugin(MicroItems.class);
	private CommandSender sender;
	private String command;
	private String[] args;

	private String denied = chat("[prefix] &cYou don't have permission to use this command.");

	public Command(CommandSender sender, String command, String[] args) {
		this.sender = sender;
		this.command = command;
		this.args = args;
	}

	public boolean run() {
		if (command.equalsIgnoreCase("microitems")) {

			String arg = chat("[prefix] &eMicroItems running &7v" + plugin.version + " &eon " + plugin.getServer().getName() + " &8(&7"
					+ plugin.getServer().getVersion() + "&8)");
			String usage = chat(
					"&cUsage: &7/" + command
					+ " <help:showitem:inventory:reload:item> [get:placeholders] <item-type:list:add:delete> <amount:placeholder-name>");
			String itemUsage = chat(
					"[prefix] Modify chat-item placeholders. \n&cUsage: &7/microitems item placeholders <list:add:delete>");

			switch (args.length) {
			case 0:
				sender.sendMessage(arg);
				sender.sendMessage(usage);
				break;
			case 1:
				arg = args[0].toLowerCase();
				switch (arg) {

				case "help":
					sender.sendMessage(usage);
					break;
				case "showitem":
					showItem(sender);
					break;
				case "displayitem":
					showItem(sender);
					break;
				case "generateitems":
					Utilities.generateItemFile();
					sender.sendMessage(chat("[prefix] &cItemMap File has been generated&8: &7/MicroTools/items.yml"));
					break;
				case "item":
					sender.sendMessage(usage);
					break;
				case "inv":
					openMainGui(sender);
					break;
				case "inventory":
					openMainGui(sender);
					break;
				case "reload":
					if (sender.hasPermission("microitems.admin")) {
						plugin.settings = new Settings(plugin);
						plugin.registry();
						sender.sendMessage(chat(plugin.settings.getValue("Chat.ReloadMessage").toString()));
						break;
					}

					sender.sendMessage(denied);
				}

				break;
			case 2:
				if (args[0].equalsIgnoreCase("item")) {
					if (args[1].equalsIgnoreCase("get")) {
						if (!(sender instanceof Player)) {
							console("[prefix] &cOnly In-Game players may use this command!");
							break;
						}

						sender.sendMessage("[prefix] &cUsage&8: &7/mitems item get <type> [amount]");
						break;
					}

					sender.sendMessage(itemUsage);
					break;
				}

				sender.sendMessage(usage);
				break;
			case 3:
				try {
					arg = args[2].toLowerCase();
				} catch (Throwable t) {
				}

				if (args[0].equalsIgnoreCase("item")) {
					switch (arg) {
					case "add":
						sender.sendMessage(itemUsage.replace("<list:add:delete>", "add <new-placeholder>"));
						break;
					case "delete":
						sender.sendMessage(itemUsage.replace("<list:add:delete>", "delete <placeholder-name>"));
					case "list":
						this.listPlaceholders(sender);
						break;
					default:
						if (args[1].equalsIgnoreCase("get")) {
							String type = arg.toUpperCase();
							int amount = plugin.settings.file().getNumber("Item.DefaultStackSize");

							giveItem(sender, type, amount);
							break;
						}

					}

				}
				break;
			}

			if (args.length > 3) {

				if (args[0].equalsIgnoreCase("item")) {

					if (sender.hasPermission("microitems.admin")) {

						String value = "";
						for (int index = 3; index < args.length; index++) {
							value = value.concat(args[index]);
						}
						List<String> placeholders = getPlaceholders();

						switch (args[2].toLowerCase()) {
						case "add":
							placeholders.add(value);
							sender.sendMessage(chat("[prefix] &aAdded placeholder&8: &7" + value));
							break;
						case "delete":
							if (placeholders.contains(value)) {
								placeholders.remove(value);
								sender.sendMessage(chat("[prefix] &aRemoved placeholder&8: &7" + value));
								break;
							}

							sender.sendMessage(chat("[prefix] &cthere isn't a placeholder by the name&8: &7" + value));
						}

						plugin.settings.setValue("Chat.ItemPlaceholders", placeholders);
						return true;
					}

					if (args[1].equalsIgnoreCase("get")) {
						String type = args[2];
						int amount = Integer.parseInt(args[3].replaceAll("[^0-9]", ""));

						giveItem(sender, type, amount);
						return true;
					}

					sender.sendMessage(denied);
					return true;
				}

				sender.sendMessage(itemUsage);
			}

			return true;
		}

		if (command.equalsIgnoreCase("item")) {
			if (!sender.hasPermission("microitems.admin")) {
				sender.sendMessage(denied);

				return true;
			}


			switch (args.length) {
			case 0:

				sender.sendMessage("[prefix] &cUsage&8: &7/item <type> [amount]");
				break;
			case 1:
				String type = args[0];
				int amount = plugin.settings.file().getNumber("Item.DefaultStackSize");

				giveItem(sender, type, amount);
				break;
			default:
				type = args[0];
				amount = Integer.parseInt(args[1].replaceAll("[^0-9]", ""));

				this.giveItem(sender, type, amount);
				break;
			}




			return true;
		}

		return plugin != null;
	}

	private List<String> getPlaceholders() {
		return plugin.settings.getList("Chat.ItemPlaceholders");
	}

	private void listPlaceholders(CommandSender sender) {
		List<String> placeholders = getPlaceholders();
		if (sender.hasPermission("microitems.admin")) {
			sender.sendMessage(chat("[prefix] &eItem Placeholders&8:&7"));
			for (String s : placeholders) {
				sender.sendMessage(chat("&8- &7" + s));
			}

		}

	}

	private void showItem(CommandSender sender) {
		if (sender.hasPermission("microitems.showitem")) {
			if (sender instanceof Player) {
				Set<Player> players = new HashSet<>();
				players.addAll(Bukkit.getOnlinePlayers());

				this.broadcastChatItem((Player) sender, "[item]", plugin.chatFormat, players, "[item]");
				return;
			}
			sender.sendMessage(chat("[prefix] &cYou can't show items on console!"));
			return;
		}

		sender.sendMessage(denied);
	}

	private void openMainGui(CommandSender sender) {
		if (sender.hasPermission("microitems.inventory") && (sender instanceof Player)) {
			Player operator = (Player) sender;

			new MainGui(chat("&2MicroItems ControlPanel")).open(operator);
			return;
		}

		sender.sendMessage(denied);
	}

	private void giveItem(CommandSender operator, String type, int amount) {
		if (operator instanceof Player) {
			Player player = (Player) operator;

			if (!player.hasPermission("microitems.item.self")) {
				player.sendMessage(chat("[prefix] &cYou don't have permission to use that!"));

				return;
			}


			if (amount < 1) {
				amount = plugin.settings.file().getNumber("Item.DefaultStackSize");
			}

			ItemStack item = Utilities.itemFromName(type.toLowerCase());
			item.setAmount(amount);

			String name = new ItemType(item.getType()).name();
			if (!name.endsWith("s") && (amount > 1)) {
				name = name.concat("s");
			}

			player.getInventory().addItem(item);
			player.sendMessage(chat("[prefix] &aYou got &7" + amount + " &e" + name));
			return;
		}

		console("[prefix] &cOnly In-Game players may use this command!");
		return;
	}

}
