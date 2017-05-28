package me.rcextract.chatassets;

import java.io.File;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.Reader;
import java.io.UnsupportedEncodingException;

import org.bukkit.configuration.file.FileConfiguration;
import org.bukkit.configuration.file.YamlConfiguration;
import org.bukkit.plugin.Plugin;

public class ConfigManager {

	private static File configfile;
	public static FileConfiguration config;
	private static File msfile;
	public static FileConfiguration msconfig;
	private static Plugin plugin;
	public ConfigManager(Main main) {
		plugin = main;
		configfile = new File(plugin.getDataFolder(), "config.yml");
		msfile = new File(plugin.getDataFolder(), "message-shortener.yml");
		File[] configs = {configfile, msfile};
		for (File file : configs) {
			if (!(file.exists())) {
				try {
					file.createNewFile();
				} catch (IOException e) {
					e.printStackTrace();
				}
				Reader defaultconfigdata = null;
				String filename;
				if (file == configfile) {
					filename = "config";
				} else {
					filename = "message-shortener";
				}
				try {
					defaultconfigdata = new InputStreamReader(plugin.getResource(filename + ".yml"), "UTF8");
				} catch (UnsupportedEncodingException e) {
					e.printStackTrace();
				}
				YamlConfiguration defaultconfig = YamlConfiguration.loadConfiguration(defaultconfigdata);
				try {
					defaultconfig.save(file);
				} catch (IOException e) {
					e.printStackTrace();
				}
			}
		}
		config = YamlConfiguration.loadConfiguration(configfile);
		msconfig = YamlConfiguration.loadConfiguration(msfile);
	}
	public static void saveConfig() {
		try {
			config.save(configfile);
		} catch (IOException e) {
			e.printStackTrace();
		}
	}
	public static void saveMsConfig() {
		try {
			msconfig.save(msfile);
		} catch (IOException e) {
			e.printStackTrace();
		}
	}
	public static FileConfiguration getConfig() {
		return config;
	}
	public static FileConfiguration getMsConfig() {
		return msconfig;
	}
	public static void reloadConfig() {
		config = YamlConfiguration.loadConfiguration(configfile);
		updateConfig();
	}
	public static void reloadMsConfig() {
		msconfig = YamlConfiguration.loadConfiguration(msfile);
		updateMsConfig();
	}
	public static void updateConfig() {
		int version = getConfig().getInt("version");
		if (version < 2) {
			Update220();
		}
	}
	public static void updateMsConfig() {
		int version = getMsConfig().getInt("version");
		if (version < 2) {
			UpdateMs220();
		}
	}
	public static void saveDefaultConfig() {
		Reader reader = null;
		try {
			reader = new InputStreamReader(plugin.getResource("config.yml"), "UTF8");
		} catch (UnsupportedEncodingException e) {
			e.printStackTrace();
		}
		config = YamlConfiguration.loadConfiguration(reader);
		try {
			getConfig().save(configfile);
		} catch (IOException e) {
			e.printStackTrace();
		}
	}
	public static void Update220() {
		getConfig().set("message-shortener", null);
		getConfig().set("version", 2);
		saveConfig();
	}
	public static void UpdateMs220() {
		Reader defaultconfigdata = null;
		try {
			defaultconfigdata = new InputStreamReader(plugin.getResource("message-shortener.yml"), "UTF8");
		} catch (UnsupportedEncodingException e) {
			e.printStackTrace();
		}
		YamlConfiguration defaultconfig = YamlConfiguration.loadConfiguration(defaultconfigdata);
		try {
			defaultconfig.save(msfile);
		} catch (IOException e) {
			e.printStackTrace();
		}
	}
}
