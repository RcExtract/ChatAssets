package me.rcextract.chatassets;

import java.util.ArrayList;
import java.util.List;

import org.bukkit.ChatColor;
import org.bukkit.command.Command;
import org.bukkit.command.CommandExecutor;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;

public class Commander implements CommandExecutor {

	private Main plugin;
	public static List<Player> addshortenmessagelist = new ArrayList<Player>();
	public static String addshortenmessagekey;
	public static String addshortenmessagemessage;
	public Commander(Main main) {
		this.plugin = main;
	}
	@Override
	public boolean onCommand(CommandSender sender, Command cmd, String label, String[] args) {
		if (cmd.getName().equalsIgnoreCase("chatassets")) {
			String prefix = plugin.getConfig().getString("prefix");
			String nopermerror = ChatColor.RED + "You do not have sufficient permission to perform this command!";
			String toomanyargserror = ChatColor.RED + "Too many arguments! " + ChatColor.YELLOW + "Do /help for help.";
			if (!(sender instanceof Player)) {
				sender.sendMessage(ChatColor.RED + "The command system can only be used by a player!");
				return true;
			}
			Player player = (Player) sender;
			if (!(player.hasPermission("chatassets.main"))) {
				Main.sendMessage(nopermerror, prefix, player);
				return true;
			}
			if (args.length == 0 || args[0].equalsIgnoreCase("help")) {
				if (!(player.hasPermission("chatassets.help"))) {
					Main.sendMessage(nopermerror, prefix, player);
					return true;
				}
				if (args.length > 1) {
					Main.sendMessage(toomanyargserror, prefix, player);
					return true;
				}
				Main.sendMessage(ChatColor.YELLOW + "This help section is taken from the bukkit help.", prefix, player);
				Main.sendMessage(ChatColor.YELLOW + "Customized help section is coming soon.", prefix, player);
				player.performCommand("help ChatAssets");
				return true;
			}
			if (args[0].equalsIgnoreCase("reload")) {
				if (!(player.hasPermission("chatassets.reload"))) {
					Main.sendMessage(nopermerror, prefix, player);
					return true;
				}
					plugin.reloadConfig();
					Main.sendMessage(ChatColor.GREEN + "Configuration file is reloaded successfully!", prefix, player);
					return true;
			}
			if (args[0].equalsIgnoreCase("messageshortener")) {
				List<String> replacestring = new ArrayList<String>();
				List<String> replacedstring = new ArrayList<String>();
				int totalmessages = plugin.getConfig().getInt("message-shortener.totalmessages");
				for (int i = 1; i <= totalmessages; i++) {
					replacestring.add(plugin.getConfig().getString("message-shortener.messages." + i + ".key"));
					replacedstring.add(plugin.getConfig().getString("message-shortener.messages." + i + ".message"));
				}
				if (args.length == 1 || args[1].equalsIgnoreCase("list")) {
					if (!(player.hasPermission("chatassets.messageshortener.view"))) {
						Main.sendMessage(nopermerror, prefix, player);
						return true;
					}
					if (args.length > 2) {
						Main.sendMessage(toomanyargserror, prefix, player);
						return true;
					}
					if (replacestring.size() == 0 && replacedstring.size() == 0) {
						Main.sendMessage(ChatColor.RED + "No shorten message exists!", prefix, player);
						return true;
					}
					for (int i = 0; i < replacestring.size() && i < replacedstring.size(); i++) {
						player.sendMessage(replacestring.get(i) + " will be replaced with " + replacedstring.get(i));
					}
					return true;
				}
				if (args[1].equalsIgnoreCase("add")) {
					if (!(player.hasPermission("chatassets.messageshortener.add"))) {
						Main.sendMessage(nopermerror, prefix, player);
						return true;
					}
					if (args.length < 3) {
						Main.sendMessage(ChatColor.RED + "Please specify a key!", prefix, player);
						return true;
					}
					if (args.length > 3) {
						Main.sendMessage(toomanyargserror, prefix, player);
						return true;
					}
					if (replacestring.contains(args[2])) {
						Main.sendMessage(ChatColor.RED + "Key is registered!", prefix, player);
						return true;
					}
					addshortenmessagelist.add(player);
					Main.sendMessage(ChatColor.YELLOW + "Please enter the message that will replace the key.", prefix, player);
					addshortenmessagekey = args[2];
					return true;
				}
			}
			return true;
		}
		return false;
	}
}
