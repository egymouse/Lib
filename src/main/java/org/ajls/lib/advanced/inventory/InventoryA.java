package org.ajls.lib.advanced.inventory;

import net.minecraft.world.Container;
import org.ajls.lib.Lib;
import org.ajls.lib.advanced.HaxhMap;
import org.bukkit.craftbukkit.inventory.CraftInventory;
import org.bukkit.craftbukkit.inventory.CraftInventoryCustom;
import org.bukkit.entity.Player;
import org.bukkit.event.inventory.InventoryType;
import org.bukkit.inventory.Inventory;
import org.bukkit.inventory.InventoryHolder;
import org.bukkit.metadata.FixedMetadataValue;

import java.util.HashMap;
import java.util.HashSet;
import java.util.UUID;

public class InventoryA {
    Inventory inventory;
    HashSet<InventoryOptions> option_value = new HashSet<>();
    HashMap<String, Object> inventoryKey_value = new HashMap<>();
    public static HashMap<UUID, HashMap<String, Object>> player_inventoryKeyMap = new HashMap<>();
    public static HaxhMap<UUID, InventoryOptions> player_inventoryOptions = new HaxhMap<>();
    static String INVENTORY_OPTION_PREFIX = "inventory_option_";
    static String INVENTORY_KEY_PREFIX = "inventory_key_";
    public InventoryA(Inventory inventory) {
        this.inventory = inventory;
    }
    public Inventory getInventory() {
        return inventory;
    }

//    public InventoryA(InventoryHolder owner, InventoryType type, Container delegate) {
//        super(owner, type, delegate);
//    }
//    public InventoryA(Container inventory) {
//        super(inventory);
//    }

//    public void setType(InventoryType type) {
//
//    }

    public void setOption(InventoryOptions option, Boolean value) {  //option not air call custom event if clicked not air
        if (value) {
            option_value.add(option);
        }
        else {
            option_value.remove(option);
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
        HashMap<String, Object> key_value =  player_inventoryKeyMap.get(player.getUniqueId());
        Object value = key_value.get(key);
        return value;
    }

    public void openedByPlayer(Player player) {
//        for (String option : option_value.keySet()) {
//            boolean value = option_value.get(option);
//            player.setMetadata(INVENTORY_OPTION_PREFIX + option, new FixedMetadataValue(Lib.getLib(), value));
//        }
        player_inventoryOptions.put(player.getUniqueId(), option_value);
        player_inventoryKeyMap.put(player.getUniqueId(), inventoryKey_value);
        player.openInventory(inventory);
//        for (String key : inventoryKey_value.keySet()) {
//            Object value = inventoryKey_value.get(key);
//            player.setMetadata(INVENTORY_KEY_PREFIX + key, new FixedMetadataValue(Lib.getLib(), value));
//        }
    }
}
