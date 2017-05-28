package me.rcextract.chatassets;

import org.bukkit.ChatColor;
import org.bukkit.configuration.file.FileConfiguration;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.player.AsyncPlayerChatEvent;

public class AntiCaseSpam implements Listener {

	//Plugin Instance
	private static FileConfiguration config;
	private static int maxUpperCases;
	private static boolean autolowercase;

	//Constructor
	public AntiCaseSpam(Main main) {
		AntiCaseSpam.config = ConfigManager.getConfig();
		maxUpperCases = config.getInt("anticasespam.max-upper-cases");
		autolowercase = config.getBoolean("anticasespam.all-char-to-lower-case-if-over-max");
	}

	
	public static int getMaxUpperCases() {
		return maxUpperCases;
	}
	
	public static void setMaxUpperCases(int maxUpperCases, boolean reload) {
		config.set("anticasespam.max-upper-cases", maxUpperCases);
		if (reload) {
			ConfigManager.saveConfig();
			ConfigManager.reloadConfig();
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
		config.set("anticasespam.all-char-to-lower-case-if-over-max", status);
		if (reload) {
			ConfigManager.saveConfig();
			ConfigManager.reloadConfig();
		}
	}
	
	@EventHandler
	public void onPlayerChat(AsyncPlayerChatEvent event) {
		Player player = event.getPlayer();
		boolean enablation = config.getBoolean("enable.anticasespam");
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
