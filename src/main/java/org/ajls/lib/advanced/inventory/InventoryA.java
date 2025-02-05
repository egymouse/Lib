package org.ajls.lib.advanced.inventory;

import net.minecraft.world.Container;
import org.ajls.lib.Lib;
import org.bukkit.craftbukkit.inventory.CraftInventory;
import org.bukkit.craftbukkit.inventory.CraftInventoryCustom;
import org.bukkit.entity.Player;
import org.bukkit.event.inventory.InventoryType;
import org.bukkit.inventory.InventoryHolder;
import org.bukkit.metadata.FixedMetadataValue;

import java.util.HashMap;
import java.util.UUID;

public class InventoryA extends CraftInventoryCustom {
    HashMap<String, Boolean> option_value;
    HashMap<String, Object> inventoryKey_value;
    public static HashMap<UUID, HashMap<String, Object>> player_inventoryKeyMap;
    static String INVENTORY_OPTION_PREFIX = "inventory_option_";
    static String INVENTORY_KEY_PREFIX = "inventory_key_";

    public InventoryA(InventoryHolder owner, InventoryType type, Container delegate) {
        super(owner, type, delegate);
    }
//    public InventoryA(Container inventory) {
//        super(inventory);
//    }

//    public void setType(InventoryType type) {
//
//    }

    public void setOption(InventoryOptions option, Boolean value) {
        if (value) {
            option_value.put(option.toString(), value);
        }
        else {
            option_value.remove(option.toString());
        }
    }

    public void put(String key, Object value) {
        inventoryKey_value.put(key, value);
    }

    public Object get(String key) {
        return inventoryKey_value.get(key);
    }

    public static Object get(Player player, String key) {
//        return player.getMetadata(INVENTORY_KEY_PREFIX + key).get(0);
        return player_inventoryKeyMap.get(player.getUniqueId()).get(key);
    }

    public void openedByPlayer(Player player) {
        for (String option : option_value.keySet()) {
            boolean value = option_value.get(option);
            player.setMetadata(INVENTORY_OPTION_PREFIX + option, new FixedMetadataValue(Lib.getLib(), value));
        }
        player_inventoryKeyMap.put(player.getUniqueId(), inventoryKey_value);
        player.openInventory(this);
//        for (String key : inventoryKey_value.keySet()) {
//            Object value = inventoryKey_value.get(key);
//            player.setMetadata(INVENTORY_KEY_PREFIX + key, new FixedMetadataValue(Lib.getLib(), value));
//        }
    }
}
