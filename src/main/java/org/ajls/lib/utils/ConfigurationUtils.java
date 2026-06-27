package org.ajls.lib.utils;

import org.bukkit.configuration.Configuration;
import org.bukkit.configuration.ConfigurationSection;

import java.util.ArrayList;
import java.util.Collection;
import java.util.List;
import java.util.UUID;
import java.util.logging.Logger;
import java.util.stream.Collectors;

public final class ConfigurationUtils {

    private ConfigurationUtils() {
    }

    public static boolean trySet(Configuration configuration, String path, Object value) {
        if (configuration.isSet(path)) {
            return false;
        }

        configuration.set(path, value);
        return true;
    }

    public static boolean tryCreateSection(Configuration configuration, String path) {
        if (configuration.isConfigurationSection(path)) {
            return false;
        }

        if (configuration.contains(path)) {
            configuration.set(path, null);
        }

        configuration.createSection(path);
        return true;
    }

    public static ConfigurationSection getOrCreateSection(Configuration configuration, String path) {
        if (configuration.isConfigurationSection(path)) {
            return configuration.getConfigurationSection(path);
        }

        if (configuration.contains(path)) {
            configuration.set(path, null);
        }

        return configuration.createSection(path);
    }

    public static List<UUID> getUuidList(Configuration configuration, String path, Logger logger) {
        List<String> stringList = configuration.getStringList(path);
        List<UUID> uuidList = new ArrayList<>();

        for (String value : stringList) {
            try {
                uuidList.add(UUID.fromString(value));
            } catch (IllegalArgumentException exception) {
                if (logger != null) {
                    logger.warning("配置路径 " + path + " 中存在无效 UUID: " + value);
                }
            }
        }

        return uuidList;
    }

    public static List<String> toUuidStringList(Collection<UUID> uuids) {
        return uuids.stream()
                .map(UUID::toString)
                .sorted()
                .collect(Collectors.toList());
    }

    public static void setUuidList(Configuration configuration, String path, Collection<UUID> uuids) {
        configuration.set(path, toUuidStringList(uuids));
    }

    public static boolean addUniqueStringToList(Configuration configuration, String path, String value) {
        List<String> list = configuration.getStringList(path);

        if (list.contains(value)) {
            return false;
        }

        list.add(value);
        configuration.set(path, list);

        return true;
    }

    public static boolean removeStringFromList(Configuration configuration, String path, String value) {
        List<String> list = configuration.getStringList(path);

        boolean removed = list.remove(value);

        if (removed) {
            configuration.set(path, list);
        }

        return removed;
    }

    public static boolean addUniqueUuidToList(Configuration configuration, String path, UUID uuid) {
        return addUniqueStringToList(configuration, path, uuid.toString());
    }

    public static boolean removeUuidFromList(Configuration configuration, String path, UUID uuid) {
        return removeStringFromList(configuration, path, uuid.toString());
    }
}
