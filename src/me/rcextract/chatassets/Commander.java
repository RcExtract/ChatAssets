package me.rcextract.chatassets;

import org.bukkit.ChatColor;
import org.bukkit.command.Command;
import org.bukkit.command.CommandExecutor;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;

import java.util.ArrayList;
import java.util.List;

public class Commander implements CommandExecutor {

	public static List<Player> addshortenmessagelist = new ArrayList<Player>();
	public static String addshortenmessagekey, addshortenmessagemessage;
	public static List<Player> msgsender = new ArrayList<Player>(), msgreceiver = new ArrayList<Player>(), setjoinmessage = new ArrayList<Player>(), setmotd = new ArrayList<Player>(), setquitmessage = new ArrayList<Player>();
	public static List<String> joinmessages = new ArrayList<String>(), quitmessages = new ArrayList<String>(), motd = new ArrayList<String>();
	private Main plugin;

	public Commander(Main main) {
		this.plugin = main;
	}

	@Override
	public boolean onCommand(CommandSender sender, Command cmd, String label, String[] args) {
		if (cmd.getName().equalsIgnoreCase("chatassets")) {
			if (!(sender instanceof Player)) {
				sender.sendMessage(ChatColor.RED + "Command chatassets can only be executed by a player!");
				return true;
			}
			Player player = (Player) sender;
			if (!(player.hasPermission("chatassets.main"))) {
				Main.sendMessage(Main.noPermError("execute this command!"), player);
				return true;
			}
			if (args.length == 0 || args[0].equalsIgnoreCase("help")) {
				if (!(player.hasPermission("chatassets.help"))) {
					Main.sendMessage(Main.noPermError("execute this command!"), player);
					return true;
				}
				player.sendMessage(ChatColor.YELLOW + "Commands:");
				player.sendMessage("/chatassets ac");
				player.sendMessage("/chatassets ms");
				player.sendMessage("/chatassets acs");
				player.sendMessage("/chatassets lh");
				player.sendMessage("/chatassets help");
				player.sendMessage("/chatassets reload");
				return true;
			}
			if (args[0].equalsIgnoreCase("reload")) {
				if (!(player.hasPermission("chatassets.reload"))) {
					Main.sendMessage(Main.noPermError("execute this command!"), player);
					return true;
				}
				plugin.reloadConfig();
				Main.sendMessage(ChatColor.GREEN + "The configuration file has been successfully reloaded.", player);
				return true;
			}
			if (args[0].equalsIgnoreCase("messageshortener") || args[0].equalsIgnoreCase("anticurse") || args[0].equalsIgnoreCase("anticasespam") || args[0].equalsIgnoreCase("loghandler")) {
				player.sendMessage(ChatColor.YELLOW + "This command is deprecated and warning will be removed soon. Please execute " + ChatColor.GRAY + ChatColor.ITALIC + "/chatassets help " + ChatColor.RESET + ChatColor.YELLOW + "for further information.");
				return true;
			}
			if (args[0].equalsIgnoreCase("ms")) {
				if (args.length == 1 || args[1].equalsIgnoreCase("list")) {
					for (String x : MessageShortener.getKeys()) {
						player.sendMessage(x + ChatColor.YELLOW + " will be replaced with " + ChatColor.RESET + MessageShortener.getMessage(x));
					}
					return true;
				}
				if (args[1].equalsIgnoreCase("add")) {
					if (!(player.hasPermission("chatassets.messageshortener.add"))) {
						Main.sendMessage(Main.noPermError("execute this command!"), player);
						return true;
					}
					if (args.length < 3) {
						Main.sendMessage(ChatColor.RED + "Please specify a key you want to register!", player);
						return true;
					}
					if (args.length > 3) {
						Main.sendMessage(ChatColor.RED + "Spaces are not allowed in a key!", player);
						return true;
					}
					Main.sendMessage(ChatColor.YELLOW + "Please enter the message that will replace the key.", player);
					addshortenmessagelist.add(player);
					addshortenmessagekey = args[2];
					return true;
				}
				if (args[1].equalsIgnoreCase("remove")) {
					if (!(player.hasPermission("chatassets.messageshortener.remove"))) {
						Main.sendMessage(Main.noPermError("execute this command!"), player);
						return true;
					}
					if (args.length < 3) {
						Main.sendMessage(ChatColor.RED + "Please specify a key you want to unregister!", player);
						return true;
					}
					if (args.length > 3) {
						Main.sendMessage(ChatColor.RED + "Spaces are not allowed in a key!", player);
						return true;
					}
					if (!(MessageShortener.getKeys().contains(args[2]))) {
						Main.sendMessage(ChatColor.RED + "Key is not registered!", player);
						return true;
					}
					MessageShortener.unRegister(args[2], true);
					Main.sendMessage(ChatColor.GREEN + "You have successfully removed a shorten message!", player);
					return true;
				}
				Main.sendMessage(ChatColor.RED + "Command does not exist!", player);
				player.performCommand("chatassets help");
				return true;
			}
			if (args[0].equalsIgnoreCase("ac")) {
				if (args.length == 1) {
					Main.sendMessage(ChatColor.RED + "Command does not exist!", player);
					player.performCommand("chatassets help");
					return true;
				}
				List<String> badwords = AntiCurse.getBadWords();
				if (args[1].equalsIgnoreCase("badwords")) {
					int pages = 0;
					if (badwords.size() % 8 == 0) {
						pages = badwords.size() / 8;
					} else {
						pages = ((badwords.size() - (badwords.size() % 8)) / 8) + 1;
					}
					if (args.length == 2) {
						for (String x : badwords) {
							if (badwords.indexOf(x) < 8) {
								player.sendMessage(Integer.toString(badwords.indexOf(x) + 1) + ". " + x);
							}
						}
						return true;
					}
					int page = 0;
					try {
						page = Integer.parseInt(args[2]);
					} catch (NumberFormatException nfe) {
						nfe.printStackTrace();
						Main.sendMessage(ChatColor.RED + "The page index must be an integer!", player);
						return true;
					}
					if (page > pages || page < 1) {
						Main.sendMessage(ChatColor.RED + "The page selected does not exist!", player);
						return true;
					}
					for (int i = 1; i <= pages; i++) {
						if (args[2].equalsIgnoreCase(Integer.toString(i))) {
							for (String x : badwords) {
								if (badwords.indexOf(x) < 8 * i && badwords.indexOf(x) > (8 * i) - 9) {
									player.sendMessage(Integer.toString(badwords.indexOf(x) + 1) + ". " + x);
								}
							}
							return true;
						}
					}
					return true;
				}
				if (args[1].equalsIgnoreCase("add")) {
					if (!(player.hasPermission("chatassets.anticurse.add"))) {
						Main.sendMessage(Main.noPermError("execute this command!"), player);
						return true;
					}
					Main.sendMessage(ChatColor.YELLOW + "Type every words you want to add in a new argument.", player);
					if (args.length == 2) {
						Main.sendMessage(ChatColor.YELLOW + "Nothing has been added to the list.", player);
						return true;
					}
					List<String> badwordsmodified = badwords;
					for (int i = 2; i < args.length; i++) {
						if (AntiCurse.getBadWords().contains(args[i])) {
							player.sendMessage(ChatColor.YELLOW + args[i] + " exists in the list. Cancel adding it.");
						} else {
							badwordsmodified.add(args[i]);
						}
					}
					AntiCurse.setBadWords(badwordsmodified, true);
					Main.sendMessage(ChatColor.GREEN + "You have successfully define a list of words as badwords.", player);
					return true;
				}
				if (args[1].equalsIgnoreCase("clear")) {
					if (!(player.hasPermission("chatassets.anticurse.clear"))) {
						Main.sendMessage(Main.noPermError("execute this command!"), player);
						return true;
					}
					List<String> blank = new ArrayList<String>();
					AntiCurse.setBadWords(blank, true);
					Main.sendMessage(ChatColor.GREEN + "You have successfully undefined all badwords.", player);
					return true;
				}
				Main.sendMessage(ChatColor.RED + "Command does not exist!", player);
				player.performCommand("chatassets help");
				return true;
			}
			if (args[0].equalsIgnoreCase("acs")) {
				if (args.length == 1 || args[1].equalsIgnoreCase("info")) {
					if (AntiCaseSpam.getAutoLowerCaseStatus()) {
						player.sendMessage(ChatColor.YELLOW + "Messages with more than " + Integer.toString(AntiCaseSpam.getMaxUpperCases()) + " upper cases will be automatically lower cased.");
						return true;
					}
					player.sendMessage(ChatColor.YELLOW + "Messages with more than " + Integer.toString(AntiCaseSpam.getMaxUpperCases()) + " upper cases will be automatically blocked.");
					return true;
				}
				if (args[1].equalsIgnoreCase("set")) {
					if (!(player.hasPermission("chatassets.anticasespam.modify"))) {
						Main.sendMessage(Main.noPermError("execute this command!"), player);
						return true;
					}
					if (args.length < 4) {
						Main.sendMessage(ChatColor.RED + "Please specify properties!", player);
						return true;
					}
					if (args.length > 4) {
						Main.sendMessage(ChatColor.RED + "Too many arguments!", player);
						player.sendMessage(ChatColor.YELLOW + "The first argument is for the maximum upper cases per sentence, and the second argument is for toggling the auto lower case system.");
						return true;
					}
					int maxuppercase;
					boolean autolowercase;
					try {
						maxuppercase = Integer.parseInt(args[2]);
					} catch (NumberFormatException nfe) {
						nfe.printStackTrace();
						Main.sendMessage(ChatColor.RED + "The maximum upper cases must be an integer!", player);
						return true;
					}
					maxuppercase = (int) maxuppercase;
					player.sendMessage(args[3]);
					if (!(args[3].equalsIgnoreCase("true") || args[3].equalsIgnoreCase("false"))) {
						Main.sendMessage(ChatColor.RED + "You can only toggle the auto lower case system with boolean!", player);
						return true;
					}
					autolowercase = Boolean.parseBoolean(args[3]);
					AntiCaseSpam.setMaxUpperCases(maxuppercase, true);
					AntiCaseSpam.setAutoLowerCaseStatus(autolowercase, true);
					Main.sendMessage(ChatColor.GREEN + "You have successfully modified the settings of anti case spam!", player);
					if (AntiCaseSpam.getAutoLowerCaseStatus()) {
						player.sendMessage(ChatColor.YELLOW + "Messages with more than " + Integer.toString(AntiCaseSpam.getMaxUpperCases()) + " upper cases will be automatically lower cased.");
						return true;
					}
					player.sendMessage(ChatColor.YELLOW + "Messages with more than " + Integer.toString(AntiCaseSpam.getMaxUpperCases()) + " upper cases will be automatically blocked.");
					return true;
				}
				Main.sendMessage(ChatColor.RED + "Command does not exist!", player);
				player.performCommand("chatassets help");
				return true;
			}
			if (args[0].equalsIgnoreCase("lh")) {
				if (args.length == 1) {
					Main.sendMessage(ChatColor.RED + "Command does not exist!", player);
					player.performCommand("chatassets help");
					return true;
				}
				if (args[1].equalsIgnoreCase("joinmessage")) {
					if (args.length == 2 || args[2].equalsIgnoreCase("view")) {
						player.sendMessage(ChatColor.YELLOW + "Join Message:");
						for (String x : LogHandler.getJoinMessages()) {
							player.sendMessage(x);
						}
						return true;
					}
					if (args[2].equalsIgnoreCase("set")) {
						if (!(player.hasPermission("chatassets.loghandler.joinmessage.set"))) {
							Main.sendMessage(Main.noPermError("execute this command!"), player);
							return true;
						}
						setjoinmessage.add(player);
						Main.sendMessage(ChatColor.YELLOW + "Every message you typed will be added into the join message.", player);
						return true;
					}
				}
				if (args[1].equalsIgnoreCase("quitmessage")) {
					if (args.length == 2 || args[2].equalsIgnoreCase("view")) {
						player.sendMessage(ChatColor.YELLOW + "Quit Message:");
						for (String x : LogHandler.getQuitMessages()) {
							player.sendMessage(x);
						}
						return true;
					}
					if (args[2].equalsIgnoreCase("set")) {
						if (!(player.hasPermission("chatassets.loghandler.quitmessage.set"))) {
							Main.sendMessage(Main.noPermError("execute this command!"), player);
							return true;
						}
						setquitmessage.add(player);
						Main.sendMessage(ChatColor.YELLOW + "Every message you typed will be added into the quit message.", player);
						return true;
					}
				}
				if (args[1].equalsIgnoreCase("motd")) {
					if (args.length == 2 || args[2].equalsIgnoreCase("view")) {
						player.sendMessage(ChatColor.YELLOW + "Motd:");
						for (String x : LogHandler.getMotd()) {
							player.sendMessage(x);
						}
						return true;
					}
					if (args[2].equalsIgnoreCase("set")) {
						if (!(player.hasPermission("chatassets.loghandler.motd.set"))) {
							Main.sendMessage(Main.noPermError("execute this command!"), player);
							return true;
						}
						setmotd.add(player);
						Main.sendMessage(ChatColor.YELLOW + "Every message you typed will be added into the motd.", player);
						return true;
					}
					return true;
				}
				Main.sendMessage(ChatColor.RED + "Command does not exist!", player);
				player.performCommand("chatassets help");
				return true;
			}
			return true;
		}
		return false;
	}
}
