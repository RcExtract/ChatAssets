package me.rcextract.chatassets;

import org.bukkit.Bukkit;
import org.bukkit.plugin.java.JavaPlugin;

public class Main extends JavaPlugin{
	
	public void onEnable() {
		getCommand("chatassets").setExecutor(new Commander(this));
		Bukkit.getPluginManager().registerEvents(new AntiCurse(this), this);
		Bukkit.getPluginManager().registerEvents(new AntiCaseSpam(this), this);
		Bukkit.getPluginManager().registerEvents(new Cooldowner(this), this);
		Bukkit.getPluginManager().registerEvents(new MessageShortener(this), this);
		getConfig().options().copyDefaults(true);
		saveConfig();
	}
	public void onDisable() {
		
	}
}
