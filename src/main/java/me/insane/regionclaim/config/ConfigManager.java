package me.insane.regionclaim.config;

import ninja.leaping.configurate.commented.CommentedConfigurationNode;
import ninja.leaping.configurate.loader.ConfigurationLoader;

import java.io.File;
import java.io.IOException;
import java.util.Arrays;

/**
 * Created by micha on 14/03/2017.
 */
public class ConfigManager {
    private static ConfigManager instance = new ConfigManager();

    public static ConfigManager getInstance() {
        return instance;
    }

    private ConfigurationLoader<CommentedConfigurationNode> configLoader;
    private CommentedConfigurationNode config;

    public void setup (File configFile, ConfigurationLoader<CommentedConfigurationNode> configLoader) {
        this.configLoader = configLoader;

        if (!configFile.exists()) {
            try {
                configFile.createNewFile();
                loadConfig();
                makeConfig();
                saveConfig();
            } catch (IOException e) {
                e.printStackTrace();
            }
        }
        else
        {
            loadConfig();
        }
    }

    private void makeConfig() {
        config.getNode("Claims").setComment("Don't edit anything below!");
    }

    public CommentedConfigurationNode getConfig() {
        return config;
    }

    public void saveConfig() {
        try {
            configLoader.save(config);
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    public void loadConfig() {
        try {
            config = configLoader.load();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }
}
