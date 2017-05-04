package me.rcextract.chatassets;

import org.bukkit.Bukkit;
import org.bukkit.entity.Player;
import org.bukkit.plugin.java.JavaPlugin;

import com.connorlinfoot.actionbarapi.ActionBarAPI;

public class Main extends JavaPlugin{
	
	public static Boolean sendmessageonactionbar;
	public void onEnable() {
		sendmessageonactionbar = getConfig().getBoolean("send-message-on-action-bar");
		getCommand("chatassets").setExecutor(new Commander(this));
		Bukkit.getPluginManager().registerEvents(new AntiCurse(this), this);
		Bukkit.getPluginManager().registerEvents(new AntiCaseSpam(this), this);
		//Cooldowner not registered
		Bukkit.getPluginManager().registerEvents(new MessageShortener(this), this);
		getConfig().options().copyDefaults(true);
		saveConfig();
	}
	public void onDisable() {
		
	}
	public static void sendMessage(String message, String prefix, Player player) {
		if (sendmessageonactionbar == false || sendmessageonactionbar == null) {
			player.sendMessage(prefix + message);
		} else {
			ActionBarAPI.sendActionBar(player, message);
		}
	}
}
