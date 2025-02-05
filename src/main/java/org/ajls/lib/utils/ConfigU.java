package org.ajls.lib.utils;

import org.bukkit.configuration.Configuration;
import org.bukkit.configuration.ConfigurationSection;
import org.bukkit.plugin.java.JavaPlugin;

public class ConfigU {
    public static Configuration getConfig(JavaPlugin javaPlugin) {
        return javaPlugin.getConfig();
    }
    public static ConfigurationSection getConfigSectionAndCreate(Configuration configuration, String path) {
        ConfigurationSection configurationSection = configuration.getConfigurationSection(path);
        if (configurationSection == null) {
            configurationSection = configuration.createSection("balance");
        }
        return configurationSection;
    }
}
