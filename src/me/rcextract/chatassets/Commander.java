package me.rcextract.chatassets;

import java.util.ArrayList;
import java.util.List;

import org.bukkit.Bukkit;
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
	public static List<Player> msgsender = new ArrayList<Player>();
	public static List<Player> msgreceiver = new ArrayList<Player>();
	public static List<Player> setjoinmessage = new ArrayList<Player>();
	public static List<Player> setmotd = new ArrayList<Player>();
	public static List<Player> setquitmessage = new ArrayList<Player>();
	public static List<String> joinmessages = new ArrayList<String>();
	public static List<String> quitmessages = new ArrayList<String>();
	public static List<String> motd = new ArrayList<String>();
	public Commander(Main main) {
		this.plugin = main;
	}
	@Override
	public boolean onCommand(CommandSender sender, Command cmd, String label, String[] args) {
		String prefix = plugin.getConfig().getString("prefix");
		if (cmd.getName().equalsIgnoreCase("chatassets")) {
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
						player.sendMessage(Main.colorcode(replacestring.get(i)) + " will be replaced with " + Main.colorcode(replacedstring.get(i)));
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
					if (setjoinmessage.contains(player) || setquitmessage.contains(player) || setmotd.contains(player)) {
						Main.sendMessage(ChatColor.RED + "You are configuring another option in configuration file! Terminate the process or save it and re-issue the command.", prefix, player);
						return true;
					}
					addshortenmessagelist.add(player);
					Main.sendMessage(ChatColor.YELLOW + "Please enter the message that will replace the key.", prefix, player);
					addshortenmessagekey = args[2];
					return true;
				}
				if (args[1].equalsIgnoreCase("remove")) {
					if (!(player.hasPermission("chatassets.messageshortener.remove"))) {
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
					if (!(replacestring.contains(args[2]))) {
						Main.sendMessage(ChatColor.RED + "Key is not registered!", prefix, player);
						return true;
					}
					String removeindex = "0";
					for (int i = 1; i <= totalmessages; i++) {
						if (args[2].equals(plugin.getConfig().getString("message-shortener.messages." + Integer.toString(i) + ".key"))) {
							removeindex = Integer.toString(i);
						}
					}
					plugin.getConfig().set("message-shortener.messages." + removeindex + ".key", null);
					plugin.getConfig().set("message-shortener.messages." + removeindex + ".message", null);
					plugin.getConfig().set("message-shortener.messages." + removeindex, null);
					plugin.saveConfig();
					plugin.reloadConfig();
					Main.sendMessage(ChatColor.GREEN + "You have successfully removed a shorten message!", prefix, player);
					return true;
				}
				return true;
			}
			if (args[0].equalsIgnoreCase("loghandler")) {
				if (args.length == 1) {
					Main.sendMessage(ChatColor.RED + "Please specify a function!", prefix, player);
					return true;
				}
				if (args[1].equalsIgnoreCase("joinmessage")) {
					if (args.length == 2 || args[2].equalsIgnoreCase("view")) {
						if (!(player.hasPermission("chatassets.loghandler.joinmessage.view"))) {
							Main.sendMessage(nopermerror, prefix, player);
							return true;
						}
						Main.sendMessage(ChatColor.GREEN + "Current player join broadcast message:", prefix, player);
						List<String> joinmessage = plugin.getConfig().getStringList("loghandler.join-message");
						for (String x : joinmessage) {
							Main.sendMessage(Main.colorcode(x), prefix, player);
						}
						return true;
					}
					if (args[2].equalsIgnoreCase("set")) {
						if (!(player.hasPermission("chatassets.loghandler.joinmessage,set"))) {
							Main.sendMessage(nopermerror, prefix, player);
							return true;
						}
						if (setquitmessage.contains(player) || setmotd.contains(player) || addshortenmessagelist.contains(player)) {
							Main.sendMessage(ChatColor.RED + "You are configuring another option in configuration file! Terminate the process or save it and re-issue the command.", prefix, player);
							return true;
						}
						Main.sendMessage(ChatColor.YELLOW + "Each line you typed will be saved in the list of player join message until you issue /chatassets loghandler done", prefix, player);
						setjoinmessage.add(player);
						return true;
					}
					return true;
				}
				if (args[1].equalsIgnoreCase("quitmessage")) {
					if (args.length == 2 || args[2].equalsIgnoreCase("view")) {
						if (!(player.hasPermission("chatassets.loghandler.quitmessage.view"))) {
							Main.sendMessage(nopermerror, prefix, player);
							return true;
						}
						Main.sendMessage(ChatColor.GREEN + "Current player quit broadcast message:", prefix, player);
						List<String> quitmessage = plugin.getConfig().getStringList("loghandler.quit-message");
						for (String x : quitmessage) {
							Main.sendMessage(Main.colorcode(x), prefix, player);
						}
						return true;
					}
					if (args[2].equalsIgnoreCase("set")) {
						if (!(player.hasPermission("chatassets.loghandler.quitmessage.set"))) {
							Main.sendMessage(nopermerror, prefix, player);
							return true;
						}
						if (setjoinmessage.contains(player) || setmotd.contains(player) || addshortenmessagelist.contains(player)) {
							Main.sendMessage(ChatColor.RED + "You are configuring another option in configuration file! Terminate the process or save it and re-issue the command.", prefix, player);
							return true;
						}
						Main.sendMessage(ChatColor.YELLOW + "Each line you typed will be saved in the list of player quit message until you issue /chatassets loghandler done", prefix, player);
						setquitmessage.add(player);
						return true;
					}
					return true;
				}
				if (args[1].equalsIgnoreCase("motd")) {
					if (args.length == 2 || args[2].equalsIgnoreCase("view")) {
						if (!(player.hasPermission("chatassets.loghandler.motd.view"))) {
							Main.sendMessage(nopermerror, prefix, player);
							return true;
						}
						Main.sendMessage(ChatColor.GREEN + "Current motd:", prefix, player);
						List<String> motd = plugin.getConfig().getStringList("loghandler.motd");
						for (String x : motd) {
							Main.sendMessage(Main.colorcode(x), prefix, player);
						}
						return true;
					}
					if (args[2].equalsIgnoreCase("set")) {
						if (!(player.hasPermission("chatassets.loghandler.motd.set"))) {
							Main.sendMessage(nopermerror, prefix, player);
						}
						if (setquitmessage.contains(player) || setjoinmessage.contains(player) || addshortenmessagelist.contains(player)) {
							Main.sendMessage(ChatColor.RED + "You are configuring another option in configuration file! Terminate the process or save it and re-issue the command.", prefix, player);
							return true;
						}
						Main.sendMessage(ChatColor.YELLOW + "Each line you typed will be saved in the list of player join message until you issue /chatassets loghandler done", prefix, player);
						setmotd.add(player);
						return true;
					}
					return true;
				}
				if (args[1].equalsIgnoreCase("done")) {
					if (setjoinmessage.contains(player)) {
						setjoinmessage.remove(player);
						plugin.getConfig().set("loghandler.join-message", joinmessages);
						plugin.saveConfig();
						plugin.reloadConfig();
						Main.sendMessage(ChatColor.GREEN + "New player join message is set!", prefix, player);
						return true;
					}
					if (setquitmessage.contains(player)) {
						setquitmessage.remove(player);
						plugin.getConfig().set("loghandler.quit-message", quitmessages);
						plugin.saveConfig();
						plugin.reloadConfig();
						Main.sendMessage(ChatColor.GREEN + "New player quit message is set!", prefix, player);
						return true;
					}
					if (setmotd.contains(player)) {
						setmotd.remove(player);
						plugin.getConfig().set("loghandler.motd", motd);
						plugin.saveConfig();
						plugin.reloadConfig();
						Main.sendMessage(ChatColor.GREEN + "New motd is set!", prefix, player);
						return true;
					}
					Main.sendMessage(ChatColor.RED + "You are not configuring an option in configuration file!", prefix, player);
					return true;
				}
				return true;
			}
			return true;
		}
		if (cmd.getName().equalsIgnoreCase("msg")) {
			String nopermerror = ChatColor.RED + "You do not have sufficient permission to perform this command!";
			if (!(sender instanceof Player)) {
				sender.sendMessage(ChatColor.RED + "The command system can only be used by a player!");
				return true;
			}
			Player player = (Player) sender;
			if (!(player.hasPermission("chatassets.msg"))) {
				Main.sendMessage(nopermerror, prefix, player);
				return true;
			}
			if (args.length == 0) {
				Main.sendMessage(ChatColor.RED + "Please specify a player you want to privately message to!", prefix, player);
				return true;
			}
			Player target = Bukkit.getServer().getPlayer(args[0]);
			if (target == null) {
				Main.sendMessage(ChatColor.RED + "Player is not found!", prefix, player);
				return true;
			}
			if (args.length == 1) {
				Main.sendMessage(ChatColor.RED + "Please specify a message!", prefix, player);
				return true;
			}
			List<String> messagewords = new ArrayList<String>();
			for (String x : args) {
				messagewords.add(x);
			}
			messagewords.remove(0);
			String finalmessage = chatautomodifier(String.join(" ", messagewords), player);
			player.sendMessage("[You -> " + target.getDisplayName() + "] " + Main.colorcode(finalmessage));
			target.sendMessage("[" + player.getDisplayName() + " -> You] " + Main.colorcode(finalmessage));
			msgsender.add(player);
			msgreceiver.add(target);
			return true;
		}
		if (cmd.getName().equalsIgnoreCase("reply")) {
			String nopermerror = ChatColor.RED + "You do not have sufficient permission to perform this command!";
			if (!(sender instanceof Player)) {
				sender.sendMessage(ChatColor.RED + "The command system can only be used by a player!");
				return true;
			}
			Player player = (Player) sender;
			if (!(player.hasPermission("chatassets.msg.reply"))) {
				Main.sendMessage(nopermerror, prefix, player);
				return true;
			}
			int index = 0;
			Player replytarget;
			if (msgsender.contains(player)) {
				index = msgsender.indexOf(player);
				replytarget = msgreceiver.get(index);
			} else if (msgreceiver.contains(player)) {
				index = msgreceiver.indexOf(player);
				replytarget = msgsender.get(index);
			} else {
				Main.sendMessage(ChatColor.RED + "Failed to reply a message!", prefix, player);
				player.sendMessage(ChatColor.YELLOW + "This is issued because no one send messsage privately to you, or you did not send message privately to someone.");
				return true;
			}
			if (args.length == 0) {
				Main.sendMessage(ChatColor.RED + "Please specify a message!", prefix, player);
				return true;
			}
			String finalmessage = chatautomodifier(String.join(" ", args), player);
			player.sendMessage("[You -> " + replytarget.getDisplayName() + "] " + Main.colorcode(finalmessage));
			replytarget.sendMessage("[" + player.getDisplayName() + " -> You] " + Main.colorcode(finalmessage));
			return true;
		}
		return false;
	}
	public String chatautomodifier(String message, Player player) {
		List<String> sentences = new ArrayList<String>();
		int index = 0;
		if (!(player.hasPermission("chatassets.basic.automodify.bypass"))) {
			for (String x : message.split("\\.")) {
				sentences.add(x);
			}
			for (String x : sentences) {
				if (!(x.startsWith(" "))) {
					index = sentences.indexOf(x);
					if (index == 0) {
						
					} else {
						String y = " " + x;
						sentences.set(index, y);
						x = y;
					}
				}
			}
			String finalmessage = String.join(".", sentences);
			if (!(finalmessage.endsWith(".")) && !(finalmessage.endsWith("?")) && !(finalmessage.endsWith("!"))) {
				finalmessage = finalmessage + ".";
			} else {
				
			}
			return finalmessage;
		} else {
			return message;
		}
	}
}
