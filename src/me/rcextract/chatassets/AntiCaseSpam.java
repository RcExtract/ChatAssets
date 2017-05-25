package me.rcextract.chatassets;

import org.bukkit.ChatColor;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.player.AsyncPlayerChatEvent;
import org.bukkit.plugin.Plugin;

public class AntiCaseSpam implements Listener {

	//Plugin Instance
	private static Plugin plugin;
	private static int maxUpperCases;
	private static boolean autolowercase;

	//Constructor
	public AntiCaseSpam(Main main) {
		AntiCaseSpam.plugin = main;
		maxUpperCases = plugin.getConfig().getInt("anticasespam.max-upper-cases");
		autolowercase = plugin.getConfig().getBoolean("anticasespam.all-char-to-lower-case-if-over-max");
	}

	
	public static int getMaxUpperCases() {
		return maxUpperCases;
	}
	
	public static void setMaxUpperCases(int maxUpperCases, boolean reload) {
		plugin.getConfig().set("anticasespam.max-upper-cases", maxUpperCases);
		if (reload) {
			plugin.saveConfig();
			plugin.reloadConfig();
		}
	}
	
	public static int getUpperCases(String message) {
		int uppercase = 0;
		for (char x : message.toCharArray()) {
			if (Character.isUpperCase(x)) {
				uppercase++;
			}
		}
		return uppercase;
	}
	
	public static boolean getAutoLowerCaseStatus() {
		return autolowercase;
	}
	
	public static void setAutoLowerCaseStatus(boolean status, boolean reload) {
		plugin.getConfig().set("anticasespam.all-char-to-lower-case-if-over-max", status);
		if (reload) {
			plugin.saveConfig();
			plugin.reloadConfig();
		}
	}
	
	@EventHandler
	public void onPlayerChat(AsyncPlayerChatEvent event) {
		Player player = event.getPlayer();
		boolean enablation = plugin.getConfig().getBoolean("enable.anticasespam");
		String message = event.getMessage();
		if (!(Main.chatHoldOn(player, "")) && enablation && (getUpperCases(message) > getMaxUpperCases())) {
			if (!(player.hasPermission("chatassets.anticasespam.bypass"))) {
				if (autolowercase) {
					event.setMessage(message.toLowerCase());
					Main.sendMessage(ChatColor.YELLOW + "Your message is lower cased becuase the original amount of upper cased characters is beyond the maximum.", player);
				} else {
					event.setCancelled(true);
					Main.sendMessage(ChatColor.YELLOW + "Your message is blocked becuase the original amount of upper cased characters is beyond the maximum.", player);
				}
			}
		}
	}
}
