package me.rcextract.chatassets;

import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.EventPriority;
import org.bukkit.event.Listener;
import org.bukkit.event.player.AsyncPlayerChatEvent;
import org.bukkit.plugin.Plugin;

import java.util.ArrayList;
import java.util.List;

public class ChatAutoModifier implements Listener {

	//Plugin Instance
	private static Plugin plugin;

	//Constructor
	public ChatAutoModifier(Main main) {
		ChatAutoModifier.plugin = main;
	}
	
	public static String autoModifyString(String sentence) {
		int index = 0;
		List<String> sentenceSplited = new ArrayList<String>();
		for (String x : sentence.split("\\.")) {
			sentenceSplited.add(x);
		}
		for (String x : sentenceSplited) {
			if (!(x.startsWith(" "))) {
				index = sentenceSplited.indexOf(x);
				if (index > 0) {
					String y = " " + x;
					sentenceSplited.set(index, y);
					x = y;
				}
			}
		}
		String finalsentence = String.join(" ", sentenceSplited);
		if (!(finalsentence.endsWith(".")) && !(finalsentence.endsWith("?")) && !(finalsentence.endsWith("!"))) {
			finalsentence = finalsentence + ".";
		}
		return finalsentence;
	}
	
	//Main
	@EventHandler(priority = EventPriority.LOW)
	public void onPlayerChat(AsyncPlayerChatEvent event) {
		Player player = event.getPlayer();
		boolean enablation = plugin.getConfig().getBoolean("enable.chatautomodifier");
		if (!(Main.chatHoldOn(player, "")) && enablation && !(player.hasPermission("chatassets.basic.automodify.bypass"))) {
		String message = event.getMessage();
		event.setMessage(Main.colorcode(autoModifyString(message)));
	}
}
}