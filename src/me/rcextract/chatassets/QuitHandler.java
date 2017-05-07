package me.rcextract.chatassets;

import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.player.PlayerQuitEvent;

public class QuitHandler implements Listener {

	public QuitHandler(Main main) {
		
	}
	@EventHandler
	public void onPlayerLeave(PlayerQuitEvent event) {
		Player player = event.getPlayer();
		int index = 0;
		if (Commander.msgsender.contains(player)) {
			index = Commander.msgsender.indexOf(player);
			Commander.msgsender.remove(index);
			Commander.msgreceiver.remove(index);
		} else if (Commander.msgreceiver.contains(player)) {
			index = Commander.msgreceiver.indexOf(player);
			Commander.msgsender.remove(index);
			Commander.msgreceiver.remove(index);
		}
	}
}
