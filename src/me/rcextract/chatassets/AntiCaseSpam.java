package me.rcextract.chatassets;

import java.util.ArrayList;
import java.util.List;

import org.bukkit.ChatColor;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.player.AsyncPlayerChatEvent;
import org.bukkit.plugin.Plugin;

public class AntiCaseSpam implements Listener {

	private Plugin plugin;
	public AntiCaseSpam(Main main) {
		this.plugin = main;
	}
	@EventHandler
	public void onPlayerChat(AsyncPlayerChatEvent event) {
		Player player = event.getPlayer();
		String prefix = plugin.getConfig().getString("prefix");
		if (Commander.addshortenmessagelist.contains(player)) {
			
		} else {
			String nopermerror = ChatColor.RED + "You do not have sufficient permission to perform this action!";
			String message = event.getMessage();
			Integer maxuppercase = plugin.getConfig().getInt("anticasespam.max-upper-cases");
			Boolean enablation = plugin.getConfig().getBoolean("enable.anticasespam");
			Boolean autotolowercase = plugin.getConfig().getBoolean("anticasespam.all-char-to-lower-case-if-over-max");
			List<Character> chargate = new ArrayList<Character>();
			if (enablation == true || enablation == null) {
				String messageinternal = message.substring(0, 1).toUpperCase() + message.substring(1);
				if (maxuppercase < 0 || maxuppercase == null) {
					
				} else {
					for (int i = 0; i < messageinternal.length(); i++) {
						if (Character.isUpperCase(messageinternal.charAt(i))) {
							chargate.add(messageinternal.charAt(i));
						}
					}
					if (chargate.size() > maxuppercase) {
						if (!(player.hasPermission("chatassets.anticasespam.bypass"))) {
							if (autotolowercase == false || autotolowercase == null) {
								event.setCancelled(true);
								Main.sendMessage(nopermerror, prefix, player);
							} else {
								event.setMessage(Main.colorcode(message.toLowerCase()));
								Main.sendMessage(ChatColor.YELLOW + "Your message has been lower cased because the amount of upper cases is over the maximum amount.", prefix, player);
							}
						} else {
							
						}
					} else {
						
					}
				}
			} else {
				
			}
		}
	}
}
