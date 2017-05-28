package me.rcextract.chatassets;

import java.util.List;

import org.bukkit.Bukkit;
import org.bukkit.ChatColor;
<<<<<<< HEAD
import org.bukkit.configuration.file.FileConfiguration;
=======
>>>>>>> b0e97d92901758e0d5600c2359a9c87798c93512
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.player.AsyncPlayerChatEvent;
import org.bukkit.event.player.PlayerJoinEvent;
import org.bukkit.event.player.PlayerQuitEvent;

public class LogHandler implements Listener {

	//ConfigManager Instance
	private static FileConfiguration config;
	private static List<String> joinmessage;
	private static List<String> quitmessage;
	private static List<String> motd;
	private static boolean enableJoinMessage;
	private static boolean enableQuitMessage;
	private static boolean enableMotd;
	
	//Constructor
	public LogHandler(Main main) {
<<<<<<< HEAD
		LogHandler.config = ConfigManager.getConfig();
		joinmessage = config.getStringList("loghandler.join-messages.message");
		quitmessage = config.getStringList("loghandler.quit-messages.message");
		motd = config.getStringList("loghandler.motd.message");
		enableJoinMessage = config.getBoolean("loghandler.join-messages.enable");
		enableQuitMessage = config.getBoolean("loghandler.quit-messages.enable");
		enableMotd = config.getBoolean("loghandler.motd.enable");
	}
	
	public static void PlaceHolderSetup(Player player) {
		int index = 0;
		for (String x : joinmessage) {
			index = joinmessage.indexOf(x);
			String y = x.replaceAll("<playername>", player.getName()).replaceAll("<displayname>", player.getDisplayName()).replaceAll("<onlineplayercount>", Integer.toString(Bukkit.getOnlinePlayers().size()));
			joinmessage.set(index, y);
		}
		for (String x : quitmessage) {
			index = quitmessage.indexOf(x);
			String y = x.replaceAll("<playername>", player.getName()).replaceAll("<displayname>", player.getDisplayName()).replaceAll("<onlineplayercount>", Integer.toString(Bukkit.getOnlinePlayers().size()));
			quitmessage.set(index, y);
		}
		for (String x : motd) {
			index = motd.indexOf(x);
			String y = x.replaceAll("<playername>", player.getName()).replaceAll("<displayname>", player.getDisplayName()).replaceAll("<onlineplayercount>", Integer.toString(Bukkit.getOnlinePlayers().size()));
			quitmessage.set(index, y);
		}
	}
	
	public static List<String> getJoinMessages() {
		return joinmessage;
	}
	
	public static void setJoinMessages(List<String> joinmessage, boolean reload) {
		config.set("loghandler.join-messages.message", joinmessage);
		if (reload) {
			ConfigManager.saveConfig();
			ConfigManager.reloadConfig();
		}
	}
	
	public static List<String> getQuitMessages() {
		return quitmessage;
	}
	
	public static void setQuitMessages(List<String> quitmessage, boolean reload) {
		config.set("loghandler.quit-messages.message", quitmessage);
		if (reload) {
			ConfigManager.saveConfig();
			ConfigManager.reloadConfig();
		}
	}
	
	public static List<String> getMotd() {
		return motd;
	}
	
	public static void setMotd(List<String> motd, boolean reload) {
		config.set("loghandler.motd.message", motd);
		if (reload) {
			ConfigManager.saveConfig();
			ConfigManager.reloadConfig();
		}
	}
	
	public static void sendMotd(Player player) {
		for (String x : motd) {
			player.sendMessage(Main.colorcode(x));
		}
	}
	
	public static void broadcastJoinMessage(String permission, PlayerJoinEvent event) {
		event.setJoinMessage(null);
		for (String x : joinmessage) {
			Bukkit.broadcast(Main.colorcode(x), permission);
		}
	}
	
	public static void broadcastQuitMessage(String permission, PlayerQuitEvent event) {
		event.setQuitMessage(null);
		for (String x : quitmessage) {
			Bukkit.broadcast(x, permission);
		}
	}
	
	//Main
	@EventHandler
	public void setLine(AsyncPlayerChatEvent event) {
		Player player = event.getPlayer();
		String line = event.getMessage();
		if (Commander.setjoinmessage.contains(player) || Commander.setquitmessage.contains(player) || Commander.setmotd.contains(player)) {
			if (!(Main.chatHoldOn(player, "setjoinmessage"))) {
				event.setCancelled(true);
				Commander.joinmessages.add(line);
				Main.sendMessage(line + ChatColor.GREEN + " has been added. " + ChatColor.YELLOW + "To stop adding lines to player join message, issue /chatassets loghandler done", player);
			} else if (!(Main.chatHoldOn(player, "setquitmessage"))) {
				event.setCancelled(true);
				Commander.quitmessages.add(line);
				Main.sendMessage(line + ChatColor.GREEN + " has been added. " + ChatColor.YELLOW + "To stop adding lines to player quit message, issue /chatassets loghandler done", player);
			} else if (!(Main.chatHoldOn(player, "setmotd"))) {
				event.setCancelled(true);
				Commander.motd.add(line);
				Main.sendMessage(line + ChatColor.GREEN + " has been added. " + ChatColor.YELLOW + "To stop adding lines to motd on join, issue /chatassets loghandler done", player);
		
			}
		}
	}
	
	//Join Handler
	@EventHandler
	public void onJoin(PlayerJoinEvent event) {
		Player player = event.getPlayer();
		PlaceHolderSetup(player);
		if (enableJoinMessage) {
			broadcastJoinMessage("chatassets.loghandler.joinmessage.receive", event);
		}
		if (player.hasPermission("chatassets.loghandler.motd.receive") && enableMotd) {
			sendMotd(player);
		}
	}
	
	public void onQuit(PlayerQuitEvent event) {
		Player player = event.getPlayer();
		PlaceHolderSetup(player);
		if (enableQuitMessage) {
			broadcastQuitMessage("chatassets.loghandler.quitmessage.receive", event);
		}
		int index = 0;
		if (Commander.msgsender.contains(player)) {
			index = Commander.msgsender.indexOf(player);
			Commander.msgsender.remove(index);
			Commander.msgreceiver.remove(index);
		} else if (Commander.msgreceiver.contains(player)) {
			index = Commander.msgreceiver.indexOf(player);
			Commander.msgsender.remove(index);
			Commander.msgreceiver.remove(index);
		}
	}
}
