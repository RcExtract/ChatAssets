package me.rcextract.chatassets;

import org.bukkit.ChatColor;
import org.bukkit.configuration.file.FileConfiguration;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.player.AsyncPlayerChatEvent;

import java.util.ArrayList;
import java.util.List;
import java.util.Set;

public class MessageShortener implements Listener {

	//ConfigManager Instance
	private static FileConfiguration config;

	//Constructor
	public MessageShortener(Main main) {
		MessageShortener.config = ConfigManager.getMsConfig();
	}

	public static List<String> getKeys() {
		List<String> keys = new ArrayList<String>();
		for (String x : config.getKeys(false)) {
			keys.add(x);
		}
		keys.remove("version");
		return keys;
	}
	
	public static String getMessage(String key) {
		String message = config.getString(key);
		return message;
	}
	
	public static void setMessage(String key, String message, boolean save) {
		config.set(key, message);
		if (save) {
			ConfigManager.saveMsConfig();
		}
	}
	
	public static void unRegister(String key, boolean reload) {
		config.set(key, null);
		if (reload) {
			ConfigManager.saveMsConfig();
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
				setMessage(addkey, addmessage, true);
				Main.sendMessage(ChatColor.GREEN + "You have successfully added a new shorten message!", player);
				player.sendMessage("Key: " + Main.colorcode(addkey));
				player.sendMessage("Message: " + Main.colorcode(addmessage));
			} else {
				if (player.hasPermission("chatassets.messageshortener.use")) {
					event.setMessage(Main.colorcode(replaceKey(message)));
				}
			}
		}
		String output = String.join(" ", messagewords);
		return output;
	}
}
