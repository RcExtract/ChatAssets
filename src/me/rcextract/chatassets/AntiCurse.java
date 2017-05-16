package me.rcextract.chatassets;

import org.bukkit.ChatColor;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.player.AsyncPlayerChatEvent;
import org.bukkit.plugin.Plugin;

import java.util.ArrayList;
import java.util.List;

public class AntiCurse implements Listener {

	private Plugin plugin;

	public AntiCurse(Main main) {
		this.plugin = main;
	}

	@EventHandler
	public void onPlayerChat(AsyncPlayerChatEvent event) {
		Player player = event.getPlayer();

		if (!Commander.addshortenmessagelist.contains(player)) {
			String prefix = plugin.getConfig().getString("prefix"), message = event.getMessage(), replacedstring = plugin.getConfig().getString("anticurse.replaced-string"), finalmessage = "";
			int index = 0;
			boolean enablation = plugin.getConfig().getBoolean("enable.anticurse"), cancelcursedmessage = plugin.getConfig().getBoolean("anticurse.cancel-cursed-message");
			List<String> messagewords = new ArrayList<String>(), badwordsconfig = plugin.getConfig().getStringList("anticurse.badwords");

			if (enablation) {
				for (String x : message.split(" "))
					messagewords.add(x.toLowerCase());

				for (int i = 0; i < badwordsconfig.size(); i++)
					badwordsconfig.set(i, badwordsconfig.get(i).toLowerCase());

				if (!(player.hasPermission("chatassets.anticurse.bypass"))) {
					for (String x : messagewords) {
						for (String y : badwordsconfig) {
							if (x.contains(y)) {
								if (!cancelcursedmessage) {
									index = messagewords.indexOf(x);
									messagewords.set(index, x.replaceAll(y, y.replaceAll("[a-zA-Z]", replacedstring)));
									x = messagewords.get(index);
									finalmessage = String.join(" ", messagewords);
									event.setMessage(Main.colorcode(finalmessage));
									Main.sendMessage(ChatColor.YELLOW + "Your curse is partially blocked.", prefix, player);
								} else {
									event.setCancelled(true);
									Main.sendMessage(ChatColor.YELLOW + "Your curse is fully blocked.", prefix, player);
								}
							}
						}
					}
				}
			}
		}
	}
}