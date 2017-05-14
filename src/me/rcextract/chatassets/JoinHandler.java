package me.rcextract.chatassets;

import java.util.List;

import org.bukkit.Bukkit;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.player.PlayerJoinEvent;
import org.bukkit.plugin.Plugin;

public class JoinHandler implements Listener {

	private Plugin plugin;
	public JoinHandler(Main main) {
		this.plugin = main;
	}
	@EventHandler
	public void onPlayerJoin(PlayerJoinEvent event) {
		Player player = event.getPlayer();
		List<String> joinmessage = plugin.getConfig().getStringList("loghandler.join-message");
		List<String> motd = plugin.getConfig().getStringList("loghandler.motd");
		String y;
		int index = 0;
		int online = Bukkit.getOnlinePlayers().size();
		for (String x : joinmessage) {
			index = joinmessage.indexOf(x);
			y = x.replaceAll("<playername>", player.getName());
			y = y.replaceAll("<displayname>", player.getDisplayName());
			y = y.replaceAll("<onlineplayercount>", Integer.toString(online));
			joinmessage.set(index, y);
			x = y;
		}
		event.setJoinMessage(null);
		for (String x : joinmessage) {
			Bukkit.broadcast(Main.colorcode(x), "chatassets.loghandler.joinmessage");
		}
		String z;
		int index2 = 0;
		for (String x : motd) {
			index2 = motd.indexOf(x);
			z = x.replaceAll("<playername", player.getName());
			z = z.replaceAll("<displayname", player.getDisplayName());
			z = z.replaceAll("<onlineplayercount>", Integer.toString(online));
			motd.set(index2, z);
			x = z;
		}
		if (!player.hasPermission("chatassets.loghandler.motd")) {
			
		} else {
			for (String x : motd) {
				player.sendMessage(Main.colorcode(x));
			}
		}
	}
}
