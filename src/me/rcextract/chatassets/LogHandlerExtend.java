package me.rcextract.chatassets;

import org.bukkit.ChatColor;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.player.AsyncPlayerChatEvent;
import org.bukkit.plugin.Plugin;

public class LogHandlerExtend implements Listener {

	private Plugin plugin;

	public LogHandlerExtend(Main main) {
		this.plugin = main;
	}

	@EventHandler
	public void onSetLine(AsyncPlayerChatEvent event) {
		Player player = event.getPlayer();
		String prefix = plugin.getConfig().getString("prefix"), line = event.getMessage();

		if (Commander.setjoinmessage.contains(player)) {
			event.setCancelled(true);
			Commander.joinmessages.add(line);
			Main.sendMessage(line + ChatColor.GREEN + " has been added. " + ChatColor.YELLOW + "To stop adding lines to player join message, issue /chatassets loghandler done", prefix, player);
		} else if (Commander.setquitmessage.contains(player)) {
			event.setCancelled(true);
			Commander.quitmessages.add(line);
			Main.sendMessage(line + ChatColor.GREEN + " has been added. " + ChatColor.YELLOW + "To stop adding lines to player quit message, issue /chatassets loghandler done", prefix, player);
		} else if (Commander.setmotd.contains(player)) {
			event.setCancelled(true);
			Commander.motd.add(line);
			Main.sendMessage(line + ChatColor.GREEN + " has been added. " + ChatColor.YELLOW + "To stop adding lines to motd on join, issue /chatassets loghandler done", prefix, player);
		}
	}
}
