package me.rcextract.chatassets;

import org.bukkit.ChatColor;
import org.bukkit.configuration.file.FileConfiguration;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.player.AsyncPlayerChatEvent;
import org.bukkit.plugin.Plugin;
import org.bukkit.scheduler.BukkitRunnable;

import java.util.ArrayList;
import java.util.List;

public class Cooldowner implements Listener {

	private FileConfiguration config;
	private Plugin plugin;

	public Cooldowner(Main main) {
		config = ConfigManager.getConfig();
		plugin = main;
	}

	@EventHandler
	public void onPlayerChat(AsyncPlayerChatEvent event) {
		Player player = event.getPlayer();
		long timecount = 20 * config.getInt("cooldowner.secs-before-next-message");
		String cooldownerror = ChatColor.YELLOW + "You need to wait for " + Long.toString(timecount / 20) + " seconds between sending two messages.";
		boolean enablation = config.getBoolean("enable.cooldowner");
		List<Player> cooldownplayer = new ArrayList<Player>();

		if (enablation) {
			if (cooldownplayer.contains(player)) {
				if (!(player.hasPermission("chatassets.cooldowner.bypass"))) {
					event.setCancelled(true);
					Main.sendMessage(cooldownerror, player);
				} else {
					cooldownplayer.remove(player);
				}
			} else {
				if (!(player.hasPermission("chatassets.cooldowner.bypass"))) {
					cooldownplayer.add(player);
					new BukkitRunnable() {

						@Override
						public void run() {
							cooldownplayer.remove(player);
						}
					}.runTaskLater(plugin, timecount);
				}
			}
		}
	}
}