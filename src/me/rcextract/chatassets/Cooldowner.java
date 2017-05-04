package me.rcextract.chatassets;

import java.util.ArrayList;
import java.util.List;

import org.bukkit.ChatColor;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.player.AsyncPlayerChatEvent;
import org.bukkit.plugin.Plugin;
import org.bukkit.scheduler.BukkitRunnable;

public class Cooldowner implements Listener {

	private Plugin plugin;
	public Cooldowner(Main main) {
		this.plugin = main;
	}
	@EventHandler
	public void onPlayerChat(AsyncPlayerChatEvent event) {
		Player player = event.getPlayer();
		long timecount = 20 * plugin.getConfig().getInt("cooldowner.secs-before-next-message");
		String prefix = plugin.getConfig().getString("prefix");
		String cooldownerror = ChatColor.YELLOW + "You need to wait for " + Long.toString(timecount / 20) + " seconds between sending two messages.";
		Boolean enablation = plugin.getConfig().getBoolean("enable.cooldowner");
		List<Player> cooldownplayer = new ArrayList<Player>();
		if (enablation == true || enablation == null) {
			if (cooldownplayer.contains(player)) {
				if (!(player.hasPermission("chatassets.cooldowner.bypass"))) {
					event.setCancelled(true);
					Main.sendMessage(cooldownerror, prefix, player);
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
					}.runTaskLater(this.plugin, timecount);
				} else {
					
				}
			}
		} else {
			
		}
	}
}