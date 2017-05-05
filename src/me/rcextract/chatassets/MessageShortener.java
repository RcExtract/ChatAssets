package me.rcextract.chatassets;

import java.util.ArrayList;
import java.util.List;

import org.bukkit.ChatColor;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.player.AsyncPlayerChatEvent;
import org.bukkit.plugin.Plugin;

public class MessageShortener implements Listener {

	private Plugin plugin;
	public MessageShortener(Main main) {
		this.plugin = main;
	}
	@EventHandler
	public void onPlayerChat(AsyncPlayerChatEvent event) {
		Player player = event.getPlayer();
		String message = event.getMessage();
		String prefix = plugin.getConfig().getString("prefix");
		int totalmessages = plugin.getConfig().getInt("message-shortener.totalmessages");
		if (Commander.addshortenmessagelist.contains(player)) {
			event.setCancelled(true);
			Commander.addshortenmessagemessage = message;
			String addmessage = message;
			String addkey = Commander.addshortenmessagekey;
			Commander.addshortenmessagelist.remove(player);
			plugin.getConfig().set("message-shortener.messages." + Integer.toString(totalmessages + 1) + ".key", addkey);
			plugin.getConfig().set("message-shortener.messages." + Integer.toString(totalmessages + 1) + ".message", addmessage);
			plugin.getConfig().set("message-shortener.totalmessages", totalmessages + 1);;
			plugin.saveConfig();
			plugin.reloadConfig();
			Main.sendMessage(ChatColor.GREEN + "You have successfully added a new shorten message!", prefix, player);
			Main.sendMessage("Key: " + addkey, prefix, player);
			Main.sendMessage("Message: " + addmessage, prefix, player);
		} else {
			int index = 0;
			String replacedstring = new String();
			String nopermerror = ChatColor.RED + "You do not have sufficient permission to use this message shortener key!";
			String finalmessage;
			List<String> messagewords = new ArrayList<String>();
			List<String> replacestring = new ArrayList<String>();
			for (String x : message.split(" ")) {
				messagewords.add(x);
			}
			for (int i = 1; i <= totalmessages; i++) {
				String x = plugin.getConfig().getString("message-shortener.messages." + i + ".key");
				replacestring.add(x);
			}
			for (String x : messagewords) {
				for (String y : replacestring) {
					if (x.contains(y)) {
						if (!(player.hasPermission("chatassets.messageshortener.use"))) {
							event.setCancelled(true);
							Main.sendMessage(nopermerror, prefix, player);
						} else {
							for (int i = 1; i <= totalmessages; i++) {
								if (y.equals(plugin.getConfig().getString("message-shortener.messages." + Integer.toString(i) + ".key"))) {
									replacedstring = plugin.getConfig().getString("message-shortener.messages." + Integer.toString(i) + ".message");
								}
							}
							index = messagewords.indexOf(x);
							messagewords.set(index, x.replaceAll(y, replacedstring));
							x = messagewords.get(index);
							finalmessage = String.join(" ", messagewords);
							event.setMessage(finalmessage);
							Main.sendMessage(ChatColor.YELLOW + "Your key has been successfully replaced with the corrosponding string.", prefix, player);
						}
					} else {
						
					}
				}
			}
		}
	}
}
