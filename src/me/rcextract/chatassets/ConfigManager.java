package me.rcextract.chatassets;

import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;

import org.bukkit.configuration.file.FileConfiguration;
import org.bukkit.configuration.file.YamlConfiguration;
import org.bukkit.plugin.Plugin;

import com.google.common.io.ByteStreams;

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
				InputStream defaultconfigdata = null;
				String filename;
				if (file == configfile) {
					filename = "config";
				} else {
					filename = "message-shortener";
				}
				defaultconfigdata = plugin.getResource(filename + ".yml");
				OutputStream output = null;
				try {
					output = new FileOutputStream(file);
				} catch (FileNotFoundException e) {
					e.printStackTrace();
				}
				try {
					ByteStreams.copy(defaultconfigdata, output);
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
		if (version < 3) {
			Update210();
			Update211();
		}
	}
	public static void updateMsConfig() {
		int version = getMsConfig().getInt("version");
		if (version < 3) {
			UpdateMs210();
			UpdateMs211();
		}
	}
	public static void saveDefaultConfig() {
		InputStream reader = plugin.getResource("config.yml");
		OutputStream output = null;
		try {
			output = new FileOutputStream(configfile);
		} catch (FileNotFoundException e) {
			e.printStackTrace();
		}
		try {
			ByteStreams.copy(reader, output);
		} catch (IOException e) {
			e.printStackTrace();
		}
	}
	public static void Update210() {
		getConfig().set("message-shortener", null);
		getConfig().set("version", 2);
		saveConfig();
	}
	public static void UpdateMs210() {
		InputStream defaultconfigdata = plugin.getResource("message-shortener.yml");
		OutputStream output = null;
		try {
			output = new FileOutputStream(configfile);
		} catch (FileNotFoundException e) {
			e.printStackTrace();
		}
		try {
			ByteStreams.copy(defaultconfigdata, output);
		} catch (IOException e) {
			e.printStackTrace();
		}
	}
	public static void Update211() {
		InputStream defaultconfigdata = plugin.getResource("config.yml");
		OutputStream output = null;
		try {
			output = new FileOutputStream(configfile);
		} catch (FileNotFoundException e) {
			e.printStackTrace();
		}
		try {
			ByteStreams.copy(defaultconfigdata, output);
		} catch (IOException e) {
			e.printStackTrace();
		}
	}
	public static void UpdateMs211() {
		InputStream defaultconfigdata = plugin.getResource("message-shortener.yml");
		OutputStream output = null;
		try {
			output = new FileOutputStream(configfile);
		} catch (FileNotFoundException e) {
			e.printStackTrace();
		}
		try {
			ByteStreams.copy(defaultconfigdata, output);
		} catch (IOException e) {
			e.printStackTrace();
		}
	}
}
