package me.rcextract.chatassets;

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

		if (!Commander.addshortenmessagelist.contains(player)) {
			String nopermerror = ChatColor.RED + "You do not have sufficient permission to perform this action!", message = event.getMessage();
			int maxuppercase = plugin.getConfig().getInt("anticasespam.max-upper-cases"), uppers = 0;
			boolean enablation = plugin.getConfig().getBoolean("enable.anticasespam"), autotolowercase = plugin.getConfig().getBoolean("anticasespam.all-char-to-lower-case-if-over-max");

			if (enablation) {
				String messageinternal = message.substring(0, 1).toUpperCase() + message.substring(1);
				if (maxuppercase > 0) {
					for (char ok : messageinternal.toCharArray())
						if (Character.isUpperCase(ok))
							uppers++;

					if (uppers > maxuppercase && !player.hasPermission("chatassets.anticasespam.bypass")) {
						if (!autotolowercase) {
							event.setCancelled(true);
							Main.sendMessage(nopermerror, prefix, player);
						} else {
							event.setMessage(Main.colorcode(message.toLowerCase()));
							Main.sendMessage(ChatColor.YELLOW + "Your message has been lower cased because the amount of upper cases is over the maximum amount.", prefix, player);
						}
					}
				}
			}
		}
	}
}
