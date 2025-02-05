package org.ajls.lib.advanced.inventory;

import org.ajls.lib.Lib;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.inventory.InventoryClickEvent;
import org.bukkit.event.inventory.InventoryCloseEvent;
import org.bukkit.inventory.Inventory;

public class InventoryListener implements Listener {
    @EventHandler
    public void onInventoryClick(InventoryClickEvent event) {
        Inventory inventory = event.getInventory();
        Player player = (Player) event.getWhoClicked();
        if (player.hasMetadata(InventoryA.INVENTORY_OPTION_PREFIX + InventoryOptions.CLICK_CANCEL)) {
            event.setCancelled(true);
        }
    }

    @EventHandler
    public void onInventoryClose(InventoryCloseEvent event) {
        Inventory inventory = event.getInventory();
        Player player = (Player) event.getPlayer();
        for (InventoryOptions inventoryOptions : InventoryOptions.values()) {
            player.removeMetadata(InventoryA.INVENTORY_OPTION_PREFIX + inventoryOptions, Lib.getLib());
        }
        InventoryA.player_inventoryKeyMap.remove(player.getUniqueId());
    }
}
