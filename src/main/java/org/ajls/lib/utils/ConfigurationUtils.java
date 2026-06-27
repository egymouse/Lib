package org.ajls.lib.utils;

import org.bukkit.configuration.Configuration;
import org.bukkit.configuration.ConfigurationSection;

public class ConfigurationUtils {

//    public static boolean initialize(Configuration configuration, String path) {
//        if (configuration.get(path) == null) {
//            configuration.set(path, "true");
//        }
//    }

    public static boolean trySet(Configuration configuration, String path, Object value) {
        if (configuration.isSet(path)) {
            return false;
        }
        configuration.set(path, value);
        return true;
    }

    public static boolean tryCreateSection(Configuration configuration, String path) {
        if (!configuration.isConfigurationSection(path)) {
            configuration.createSection(path);
            return true;
        }
        return false;

        //FileConfiguration config = getConfig();
        //
        //ConfigurationSection selection;
        //
        //if (config.isConfigurationSection("selection")) {
        //    // 已经是 section，直接拿来用，不会覆盖
        //    selection = config.getConfigurationSection("selection");
        //} else if (config.contains("selection")) {
        //    // selection 存在，但不是 section，例如 selection: hello
        //    getLogger().warning("config.yml 中 selection 已存在，但它不是一个配置节点！");
        //    getLogger().warning("请把 selection 改成类似 selection: {} 的形式。");
        //    return;
        //} else {
        //    // selection 不存在，创建新的空 section
        //    selection = config.createSection("selection");
        //    saveConfig();
        //}
    }

    public static ConfigurationSection getOrCreateSection(Configuration configuration, String path) {
        if (!configuration.isConfigurationSection(path)) {
            return configuration.createSection(path);
        }
        return configuration.getConfigurationSection(path);
    }
}
