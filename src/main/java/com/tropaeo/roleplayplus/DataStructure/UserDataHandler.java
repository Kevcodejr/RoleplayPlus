package com.tropaeo.roleplayplus.DataStructure;

import org.bukkit.configuration.InvalidConfigurationException;
import org.bukkit.configuration.file.FileConfiguration;
import org.bukkit.configuration.file.YamlConfiguration;
import org.bukkit.plugin.java.JavaPlugin;

import java.io.File;
import java.io.IOException;

public class UserDataHandler {
    private String path = "";
    private boolean initialized;

    public UserDataHandler() {
        initialized = false;
    }

    public void initialize(JavaPlugin plugin) {
        path = plugin.getDataFolder().getAbsolutePath();
        initialized = true;
    }

    public FileConfiguration load(String uuid) {
        if (!initialized) {
            System.out.println("UserDataHandler has not been initialized!");
            return null;
        }
        File file = new File(path + "/userdata/" + uuid + ".yml");
        try {
            if (file.exists()) {
                FileConfiguration config = new YamlConfiguration();
                config.load(file);
                return config;
            }
        } catch (IOException | InvalidConfigurationException e) {
            System.out.println("Loading user data threw an exception. Could not continue for " + uuid);
            e.printStackTrace();
        }
        return null;
    }

    public void save(String uuid, FileConfiguration config) {
        if (!initialized) {
            System.out.println("UserDataHandler has not been initialized!");
            return;
        }
        try {
            File file = new File(path + "/userdata/" + uuid + ".yml");
            config.save(file);
        } catch (IOException e) {
            System.out.println("Saving user data threw an exception. Could not continue for " + uuid);
            e.printStackTrace();
        }
    }

    public void delete(String uuid){
        File file = new File(path + "/userdata/" + uuid + ".yml");
        if(file.exists()){
            file.delete();
        }
    }
}
