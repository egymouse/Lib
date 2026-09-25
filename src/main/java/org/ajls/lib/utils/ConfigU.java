package org.ajls.lib.utils;

import org.bukkit.Bukkit;
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
            configurationSection = configuration.createSection(path);
        }
        return configurationSection;
    }


    public static void remove(Configuration configuration, String path) {
        configuration.set(path, null);
        String parentPath = getParentPath(path);
        if (parentPath == null) {return;}
        ConfigurationSection section = configuration.getConfigurationSection(parentPath);

        if (section == null) {
            // 该路径不存在，视为空
            Bukkit.broadcastMessage("Lib parent config section not found");
        } else if (section.getKeys(false).isEmpty()) {
            // 路径存在，但下面没有任何子节点（即空白占位符）
            // 比如配置里写了 "Test.Parent:" 但后面没跟任何值
            configuration.set(parentPath, null);
        }

    }

    public static String getParentPath(String path) {
        String parentPath = null;

// 找到最后一个 '.' 的位置
        int lastDotIndex = path.lastIndexOf('.');
        if (lastDotIndex != -1) {
            // 截取 '.' 之前的部分作为父路径
            parentPath = path.substring(0, lastDotIndex);
        } else {
            // 如果没有 '.'，说明已经是根路径，没有父级
            parentPath = null;
        }
        return parentPath;

// parentPath 结果为 "Test.Parent"
    }
}
