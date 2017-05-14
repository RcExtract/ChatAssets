package me.rcextract.chatassets;

import java.util.List;

import org.bukkit.Bukkit;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.player.PlayerQuitEvent;
import org.bukkit.plugin.Plugin;

public class QuitHandler implements Listener {

	private Plugin plugin;
	public QuitHandler(Main main) {
		this.plugin = main;
	}
	@EventHandler
	public void onPlayerLeave(PlayerQuitEvent event) {
		Player player = event.getPlayer();
		List<String> leavemessage = plugin.getConfig().getStringList("loghandler.quit-message");
		String y;
		int forindex = 0;
		int online = Bukkit.getOnlinePlayers().size();
		for (String x : leavemessage) {
			forindex = leavemessage.indexOf(x);
			y = x.replaceAll("<playername>", player.getName());
			y = y.replaceAll("<displayname>", player.getDisplayName());
			y = y.replaceAll("<onlineplayercount>", Integer.toString(online));
			leavemessage.set(forindex, y);
			x = y;
		}
		int index = 0;
		if (Commander.msgsender.contains(player)) {
			index = Commander.msgsender.indexOf(player);
			Commander.msgsender.remove(index);
			Commander.msgreceiver.remove(index);
		} else if (Commander.msgreceiver.contains(player)) {
			index = Commander.msgreceiver.indexOf(player);
			Commander.msgsender.remove(index);
			Commander.msgreceiver.remove(index);
		} else {
			
		}
		event.setQuitMessage(null);
		for (String x : leavemessage) {
			Bukkit.broadcast(Main.colorcode(x), "chatassets.loghandler.quitmessage");
		}
	}
}
