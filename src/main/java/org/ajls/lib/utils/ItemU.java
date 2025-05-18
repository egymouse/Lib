package org.ajls.lib.utils;

import org.bukkit.NamespacedKey;
import org.bukkit.entity.Item;
import org.bukkit.persistence.PersistentDataType;

public class ItemU {
    public static Item setStringPersistentData(Item item, NamespacedKey namespacedKey, String value) {
        if (item == null) return null;
        item.getPersistentDataContainer().set(namespacedKey, PersistentDataType.STRING, value);
        return item;
    }
}
