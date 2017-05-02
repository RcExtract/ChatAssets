package me.rcextract.chatassets;

import java.util.ArrayList;
import java.util.List;

import org.bukkit.ChatColor;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.player.AsyncPlayerChatEvent;
import org.bukkit.plugin.Plugin;

import com.connorlinfoot.actionbarapi.ActionBarAPI;

public class AntiCurse implements Listener {

	private Plugin plugin;
	public AntiCurse(Main main) {
		this.plugin = main;
	}
	@EventHandler
	public void onPlayerChat(AsyncPlayerChatEvent event) {
		Player player = event.getPlayer();
		String prefix = plugin.getConfig().getString("prefix");
		String message = event.getMessage();
		String replacedstring = plugin.getConfig().getString("anticurse.replaced-string");
		String finalmessage;
		int index = 0;
		Boolean enablation = plugin.getConfig().getBoolean("enable.anticurse");
		Boolean cancelcursedmessage = plugin.getConfig().getBoolean("anticurse.cancel-cursed-message");
		List<String> messagewords = new ArrayList<String>();
		List<String> badwordsconfig = plugin.getConfig().getStringList("anticurse.badwords");
		if (enablation == true || enablation == null) {
			for (String x : message.split(" ")) {
				messagewords.add(x.toLowerCase());
			}
			for (int i = 0; i < badwordsconfig.size(); i++) {
				badwordsconfig.set(i, badwordsconfig.get(i).toLowerCase());
			}
			if (!(player.hasPermission("chatassets.anticurse.bypass"))) {
				for (String x : messagewords) {
					for (String y : badwordsconfig) {
						if (x.contains(y)) {
							if (cancelcursedmessage == false || cancelcursedmessage == null) {
								index = messagewords.indexOf(x);
								messagewords.set(index, x.replaceAll(y, y.replaceAll("[a-zA-Z]", replacedstring)));
								x = messagewords.get(index);
								finalmessage = String.join(" ", messagewords);
								event.setMessage(finalmessage);
								sendMessage(ChatColor.YELLOW + "Your curse is partially blocked.", prefix, player);
							} else {
								event.setCancelled(true);
								sendMessage(ChatColor.YELLOW + "Your curse is fully blocked.", prefix, player);
							}
						} else {
							
						}
					}
				}
			} else {
				
			}
		} else {

		}
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
