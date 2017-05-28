package me.rcextract.chatassets;

import com.connorlinfoot.actionbarapi.ActionBarAPI;

import org.bukkit.Bukkit;
import org.bukkit.ChatColor;
import org.bukkit.configuration.file.FileConfiguration;
import org.bukkit.entity.Player;
import org.bukkit.event.Listener;
import org.bukkit.plugin.java.JavaPlugin;

public class Main extends JavaPlugin {

	//Plugin Instance
	private static FileConfiguration config;
	public static String prefix;
	
	//onEnable
	@Override
	public void onEnable() {
		new ConfigManager(this);
		config = ConfigManager.getConfig();
		ConfigManager.updateConfig();
		ConfigManager.updateMsConfig();
		prefix = config.getString("prefix");
		Commander commander = new Commander(this);
		getCommand("chatassets").setExecutor(commander);
		this.registerEvents(new ChatAutoModifier(this));
		this.registerEvents(new AntiCurse(this));
		this.registerEvents(new AntiCaseSpam(this));
		//Cooldowner not registered
		this.registerEvents(new MessageShortener(this));
		this.registerEvents(new LogHandler(this));
		this.registerEvents(new PrivateMessenger(this));
	}

	//onDisable
	@Override
	public void onDisable() {
		saveConfig();
	}
	
	//Messenger Handler
	public static void sendMessage(String message, Player player) {
		boolean sendmessageonactionbar = Bukkit.getPluginManager().isPluginEnabled("ActionBarAPI") && config.getBoolean("send-message-on-action-bar");
		if (sendmessageonactionbar)
			ActionBarAPI.sendActionBar(player, message);
		else 
			player.sendMessage(prefix + message);
	}

	//Color Code Translation
	public static String colorcode(String string) {
		return ChatColor.translateAlternateColorCodes('&', string);
	}
	
	//Event Registration
	public void registerEvents(Listener listener) {
		Bukkit.getPluginManager().registerEvents(listener, this);
		
	}
	
	//No Permission Error
	public static String noPermError(String action) {
		String nopermerror = ChatColor.RED + "You do not have sufficient permission to " + action + "!";
		return nopermerror;
	}
	
	//Chat Hold on
	public static boolean chatHoldOn(Player player, String playerListException) {
		boolean returnboolean = false;
		if (playerListException.equals("addshortenmessagelist")) {
			if (Commander.setjoinmessage.contains(player) || Commander.setquitmessage.contains(player) || Commander.setmotd.contains(player)) {
				returnboolean = true;
			}
		} else if (playerListException.equals("setjoinmessage")) {
			if (Commander.addshortenmessagelist.contains(player) || Commander.setquitmessage.contains(player) || Commander.setmotd.contains(player)) {
				returnboolean = true;
			}
		} else if (playerListException.equals("setquitmessage")) {
			if (Commander.addshortenmessagelist.contains(player) || Commander.setjoinmessage.contains(player) || Commander.setmotd.contains(player)) {
				returnboolean = true;
			}
		} else if (playerListException.equals("setmotd")) {
			if (Commander.addshortenmessagelist.contains(player) || Commander.setquitmessage.contains(player) || Commander.setmotd.contains(player)) {
				returnboolean = true;
			}
		} else {
			if (Commander.addshortenmessagelist.contains(player) || Commander.setjoinmessage.contains(player) || Commander.setquitmessage.contains(player) || Commander.setmotd.contains(player)) {
				returnboolean = true;
			}
		}
		return returnboolean;
	}
}
