package me.rcextract.chatassets;

import com.connorlinfoot.actionbarapi.ActionBarAPI;
import org.bukkit.Bukkit;
import org.bukkit.ChatColor;
import org.bukkit.entity.Player;
import org.bukkit.event.Listener;
import org.bukkit.plugin.java.JavaPlugin;

public class Main extends JavaPlugin {
	public static boolean sendmessageonactionbar;

	public static void sendMessage(String message, String prefix, Player player) {
		if (sendmessageonactionbar)
			ActionBarAPI.sendActionBar(player, message);
		else
			player.sendMessage(prefix + message);
	}

	public static String colorcode(String string) {
		return ChatColor.translateAlternateColorCodes('&', string);
	}

	@Override
	public void onEnable() {
		sendmessageonactionbar = Bukkit.getPluginManager().isPluginEnabled("ActionBarAPI") && getConfig().getBoolean("send-message-on-action-bar");

		Commander commander = new Commander(this);
		getCommand("chatassets").setExecutor(commander);
		getCommand("reply").setExecutor(commander);
		getCommand("msg").setExecutor(commander);
		this.registerEvents(new ChatAutoModifier(this));
		this.registerEvents(new AntiCurse(this));
		this.registerEvents(new AntiCaseSpam(this));
		//Cooldowner not registered
		this.registerEvents(new MessageShortener(this));
		this.registerEvents(new QuitHandler(this));
		this.registerEvents(new JoinHandler(this));
		this.registerEvents(new LogHandlerExtend(this));
		getConfig().options().copyDefaults(true);
		saveConfig();
	}

	@Override
	public void onDisable() {
		saveConfig();
	}

	public void registerEvents(Listener listener) {
		Bukkit.getPluginManager().registerEvents(listener, this);
	}
}
