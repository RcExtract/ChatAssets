package me.rcextract.chatassets;

import java.util.ArrayList;
import java.util.List;

import org.bukkit.ChatColor;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.EventPriority;
import org.bukkit.event.Listener;
import org.bukkit.event.player.AsyncPlayerChatEvent;
import org.bukkit.plugin.Plugin;

public class ChatAutoModifier implements Listener {

	private Plugin plugin;
	public ChatAutoModifier(Main main) {
		this.plugin = main;
	}
	@EventHandler(priority = EventPriority.LOW)
	public void onPlayerChat(AsyncPlayerChatEvent event) {
		Player player = event.getPlayer();
		String message = event.getMessage();
		String nopermerror = ChatColor.RED + "You do not have sufficient permission to chat!";
		String prefix = plugin.getConfig().getString("prefix");
		int index = 0;
		List<String> sentences = new ArrayList<String>();
		Boolean enablation = plugin.getConfig().getBoolean("enable.chatautomodifier");
		if (enablation == true || enablation == null) {
			if (!(player.hasPermission("chatassets.basic.chat"))) {
				event.setCancelled(true);
				Main.sendMessage(nopermerror, prefix, player);
			} else {
				if (!(player.hasPermission("chatassets.basic.automodify.bypass"))) {
					for (String x : message.split("\\.")) {
						sentences.add(x);
					}
					for (String x : sentences) {
						if (!(x.startsWith(" "))) {
							index = sentences.indexOf(x);
							if (index == 0) {
								
							} else {
								String y = " " + x;
								sentences.set(index, y);
								x = y;
							}
						}
					}
					String finalmessage = String.join(".", sentences);
					if (!(finalmessage.endsWith(".")) && !(finalmessage.endsWith("?")) && !(finalmessage.endsWith("!"))) {
						finalmessage = finalmessage + ".";
					} else {
						
					}
					event.setMessage(Main.colorcode(finalmessage));
				}
			}
		}
	}
}
