package me.rcextract.chatassets;

import java.util.ArrayList;
import java.util.List;

import org.bukkit.Bukkit;
import org.bukkit.ChatColor;
import org.bukkit.configuration.file.FileConfiguration;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.player.AsyncPlayerChatEvent;

public class PrivateMessenger implements Listener {

	private FileConfiguration config;
	public PrivateMessenger(Main main) {
		config = ConfigManager.getConfig();
	}
	@EventHandler
	public void onPrivateTag(AsyncPlayerChatEvent event) {
		Player player = event.getPlayer();
		boolean enablation = config.getBoolean("enable.private-messenger");
		String message = event.getMessage();
		if (!(Main.chatHoldOn(player, "")) && enablation && message.startsWith("@")) {
			event.setCancelled(true);
			if (!(player.hasPermission("chatassets.msg"))) {
				Main.sendMessage(Main.noPermError("chat privately!"), player);
			} else {
				List<String> messagewords = new ArrayList<String>();
				for (String x : message.split(" ")) {
					messagewords.add(x);
				}
				String targetname = messagewords.get(0).substring(1);
				Player target = Bukkit.getPlayer(targetname);
				if (target == null) {
					Main.sendMessage(ChatColor.RED + "Player cannot be found!", player);
				} else {
					messagewords.remove(0);
					target.sendMessage("[" + player.getDisplayName() + " -> You] " + AntiCurse.replaceBadWords(ChatAutoModifier.autoModifyString(String.join(" ", messagewords))));
					player.sendMessage("[You -> " + target.getDisplayName() + "] " + AntiCurse.replaceBadWords(ChatAutoModifier.autoModifyString(String.join(" ", messagewords))));
				}
			}
		}
	}
}
