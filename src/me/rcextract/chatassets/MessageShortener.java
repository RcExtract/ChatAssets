package me.rcextract.chatassets;

import org.bukkit.ChatColor;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.player.AsyncPlayerChatEvent;
import org.bukkit.plugin.Plugin;

import java.util.ArrayList;
import java.util.List;
import java.util.Set;

public class MessageShortener implements Listener {

	//Plugin Instance
	private static Plugin plugin;

	//Constructor
	public MessageShortener(Main main) {
		MessageShortener.plugin = main;
	}

	public static List<String> getKeys() {
		List<String> keys = new ArrayList<String>();
		for (int i = 1; i <= totaloptions(); i++) {
			String x = plugin.getConfig().getString("message-shortener.messages." + Integer.toString(i) + ".key");
			keys.add(x);
		}
		return keys;
	}
	
	public static String getMessage(String key) {
		String message = "";
		for (int i = 1; i <= totaloptions(); i++) {
			String x = plugin.getConfig().getString("message-shortener.messages." + Integer.toString(i) + ".key");
			if (x.equals(key)) {
				message = plugin.getConfig().getString("message-shortener.messages." + Integer.toString(i) + ".message");
			}
		}
		return message;
	}
	
	public static void setMessage(String key, String message, boolean reload) {
		for (int i = 1; i <= totaloptions(); i++) {
			String x = plugin.getConfig().getString("message-shortener.messages." + Integer.toString(i) + ".key");
			if (x.equals(key)) {
				plugin.getConfig().set("message-shortener.messages." + Integer.toString(i) + ".message", message);
			}
		}
		if (reload) {
			plugin.saveConfig();
			plugin.reloadConfig();
		}
	}
	
	public static void addMessage(String key, String message, boolean reload) {
		plugin.getConfig().set("message-shortener.messages." + Integer.toString(totaloptions() + 1) + ".key", key);
		plugin.getConfig().set("message-shortener.messages." + Integer.toString(totaloptions()) + ".message", message);
		if (reload) {
			plugin.saveConfig();
			plugin.reloadConfig();
		}
	}
	
	public static void unRegister(String key, boolean reload) {
		int index = 0;
		for (int i = 1; i <= totaloptions(); i++) {
			String x = plugin.getConfig().getString("message-shortener.messages." + Integer.toString(i) + ".key");
			if (x.equals(key)) {
				index = i;
				plugin.getConfig().set("message-shortener.messages." + Integer.toString(i) + ".key", null);
				plugin.getConfig().set("message-shortener.messages." + Integer.toString(i) + ".message", null);
				plugin.getConfig().set("message-shortener.messages." + Integer.toString(i), null);
			}
		}
		for (String x : plugin.getConfig().getConfigurationSection("message-shortener.messages").getKeys(false)) {
			if (Integer.parseInt(x) > index) {
				String thatkey = plugin.getConfig().getString("message-shortener.messages." + x + ".key");
				String thatmessage = plugin.getConfig().getString("message-shortener.messages." + x + ".message");
				plugin.getConfig().set("message-shortener.messages." + Integer.toString(Integer.parseInt(x) - 1) + ".key", thatkey);
				plugin.getConfig().set("message-shortener.messages." + Integer.toString(Integer.parseInt(x) - 1) + ".message", thatmessage);
				plugin.getConfig().set("message-shortener.messages." + x + ".key", null);
				plugin.getConfig().set("message-shortener.messages." + x + ".message", null);
				plugin.getConfig().set("message-shortener.messages." + x, null);
			}
		}
		if (reload) {
			plugin.saveConfig();
			plugin.reloadConfig();
		}
	}
	
	public static String replaceKey(String message) {
		int index = 0;
		List<String> messagewords = new ArrayList<String>();
		List<String> replacestrings = getKeys();
		String replacemessage;
		for (String x : message.split(" ")) {
			messagewords.add(x);
		}
		for (String x : messagewords) {
			for (String y : replacestrings) {
				if (x.contains(y)) {
					replacemessage = getMessage(y);
					index = messagewords.indexOf(x);
					messagewords.set(index, x.replaceAll(y, replacemessage));
					x = x.replaceAll(y, replacemessage);
				}
			}
		}
		String output = String.join(" ", messagewords);
		return output;
	}
	//Main
	@EventHandler
	public void onPlayerChat(AsyncPlayerChatEvent event) {
		Player player = event.getPlayer();
		String message = event.getMessage();
		if (!(Main.chatHoldOn(player, "addshortenmessagelist"))) {
			if (Commander.addshortenmessagelist.contains(player)) {
				event.setCancelled(true);
				Commander.addshortenmessagemessage = message;
				String addmessage = message, addkey = Commander.addshortenmessagekey;
				Commander.addshortenmessagelist.remove(player);
				addMessage(addkey, addmessage, true);
				Main.sendMessage(ChatColor.GREEN + "You have successfully added a new shorten message!", player);
				player.sendMessage("Key: " + Main.colorcode(addkey));
				player.sendMessage("Message: " + Main.colorcode(addmessage));
			} else {
				if (player.hasPermission("chatassets.messageshortener.use")) {
					event.setMessage(Main.colorcode(replaceKey(message)));
				}
			}
		}
	}
	public static int totaloptions() {
		Set<String> keys = plugin.getConfig().getConfigurationSection("message-shortener.messages").getKeys(false);
		int largest = 0;
		for (String i : keys) {
			if (Integer.parseInt(i) > largest) {
				largest = Integer.parseInt(i);
			}
		}
		return largest;
	}
}
