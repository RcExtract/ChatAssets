package me.rcextract.chatassets;

import java.util.ArrayList;
import java.util.List;

import org.bukkit.ChatColor;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.player.AsyncPlayerChatEvent;
import org.bukkit.plugin.Plugin;

public class AntiCurse implements Listener {

	//Plugin Instance
	private static Plugin plugin;
	private static List<String> badwords;
	private static boolean status;
	//Constructor
	public AntiCurse(Main main) {
		AntiCurse.plugin = main;
		badwords = plugin.getConfig().getStringList("anticurse.badwords");
		status = plugin.getConfig().getBoolean("anticurse.cancel-cursed-message");
	}
		
	public static List<String> getBadWords() {
		return badwords;
	}
	
	public static void setBadWords(List<String> newbadwords, boolean reload) {
		plugin.getConfig().set("anticurse.badwords", newbadwords);
		if (reload) {
			plugin.saveConfig();
			plugin.reloadConfig();
		}
	}
	
	public static String replaceBadWords(String message) {
		int index = 0;
		List<String> messagewords = new ArrayList<String>();
		String replacedstring = plugin.getConfig().getString("anticurse.replaced-char");
		String finalmessage;
		for (String x : message.split(" ")) {
			messagewords.add(x.toLowerCase());
		}
		List<String> badwords = plugin.getConfig().getStringList("anticurse.badwords");
		for (String x : badwords) {
			index = badwords.indexOf(x);
			badwords.set(index, x.toLowerCase());
		}
		for (String x : messagewords) {
			for (String y : badwords) {
				if (x.contains(y)) {
					index = messagewords.indexOf(x);
					messagewords.set(index, x.replaceAll(y, y.replaceAll(".", replacedstring)));
					x = messagewords.get(index);
				}
			}
		}
		finalmessage = String.join(" ", messagewords);
		return finalmessage;
	}
	
	public static boolean containsBadWord(String message) {
		boolean returnboolean = false;
		for (String x : badwords) {
			if (message.contains(x)) {
				returnboolean = true;
			}
		}
		return returnboolean;
	}
	
	public static String getReplacedChar() {
		String replacedchar = plugin.getConfig().getString("anticurse.replaced-string");
		return replacedchar;
	}
	
	public static void setReplacedChar(String character, boolean reload) {
		plugin.getConfig().set("anticurse.replaced-string", character);
		if (reload) {
			plugin.saveConfig();
			plugin.reloadConfig();
		}
	}
	
	public static boolean getCancelCurseMessageStatus() {
		return status;
	}
	
	public static void setCancelCurseMessageStatus(boolean status, boolean reload) {
		plugin.getConfig().set("anticurse.cancel-cursed-message", status);
		if (reload) {
			plugin.saveConfig();
			plugin.reloadConfig();
		}
	}
	
	//Main
	@EventHandler
	public void onPlayerChat(AsyncPlayerChatEvent event) {
		Player player = event.getPlayer();
		boolean enablation = plugin.getConfig().getBoolean("enable.anticurse");
		String message = event.getMessage();
		if (!(Main.chatHoldOn(player, "")) && enablation && containsBadWord(message) && !(player.hasPermission("chatassets.anticurse.bypass"))) {
			if (status) {
				event.setCancelled(true);
				Main.sendMessage(ChatColor.RED + "Your message with badword(s) is blocked.", player);
			} else {
				event.setMessage(AntiCurse.replaceBadWords(message));
				Main.sendMessage(ChatColor.RED + "Your cursing is blocked.", player);
			}
		}
	}
}