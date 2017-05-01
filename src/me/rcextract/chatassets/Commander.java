package me.rcextract.chatassets;

import org.bukkit.ChatColor;
import org.bukkit.command.Command;
import org.bukkit.command.CommandExecutor;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;

import com.connorlinfoot.actionbarapi.ActionBarAPI;

public class Commander implements CommandExecutor {

	private Main plugin;
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
				sendMessage(nopermerror, prefix, player);
				return true;
			}
			if (args.length == 0 || args[0].equalsIgnoreCase("help")) {
				if (!(player.hasPermission("chatassets.help"))) {
					sendMessage(nopermerror, prefix, player);
					return true;
				}
				if (args.length > 1) {
					sendMessage(toomanyargserror, prefix, player);
					return true;
				}
				sendMessage(ChatColor.YELLOW + "This help section is taken from the bukkit help.", prefix, player);
				sendMessage(ChatColor.YELLOW + "Customized help section is coming soon.", prefix, player);
				player.performCommand("help ChatAssets");
				return true;
			}
			if (args[0].equalsIgnoreCase("reload")) {
				if (!(player.hasPermission("chatassets.reload"))) {
					sendMessage(nopermerror, prefix, player);
					return true;
				}
					plugin.reloadConfig();
					sendMessage(ChatColor.GREEN + "Configuration file is reloaded successfully!", prefix, player);
					return true;
			}
			return true;
		}
		return false;
	}
	private void sendMessage(String message, String prefix, Player player) {
		Boolean sendmessageonactionbar = plugin.getConfig().getBoolean("send-message-on-action-bar");
		if (sendmessageonactionbar == false || sendmessageonactionbar == null) {
			player.sendMessage(prefix + message);
		} else {
			ActionBarAPI.sendActionBar(player, message);
		}
	}
}
