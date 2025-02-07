package org.ajls.lib.advanced.inventory;

import org.ajls.lib.advanced.HaxhMap;
import org.ajls.lib.advanced.customevents.InventoryClickNotAirEvent;
import org.ajls.lib.utils.BukkitU;
import org.bukkit.Material;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.inventory.InventoryClickEvent;
import org.bukkit.event.inventory.InventoryCloseEvent;
import org.bukkit.inventory.Inventory;

import java.util.HashSet;
import java.util.UUID;

public class InventoryListener implements Listener {
//    HaxhMap<InventoryClickEvent, >
    @EventHandler
    public void onInventoryClick(InventoryClickEvent event) {
        Inventory inventory = event.getInventory();
        Player player = (Player) event.getWhoClicked();
        UUID uuid = player.getUniqueId();
        if (InventoryA.player_inventoryOptions.contains(uuid, InventoryOptions.CLICK_CANCEL)) {
            event.setCancelled(true);
        }
//        if (InventoryA.player_inventoryOptions.contains(uuid, InventoryOptions.IGNORE_AIR_CLICK)) {
//
//        }
        if (event.getCurrentItem() == null) {
//            player.sendMessage("clicked null how can that happen");
            return;
        }
//        if (event.getCurrentItem().getType() != Material.AIR) {   //use haxhmap to put event and boolean
//            InventoryClickEventA inventoryClickEventA = (InventoryClickEventA) event;
//            inventoryClickEventA.setAir(false);
//            BukkitU.callEvent(inventoryClickEventA);
//        }

//        if (player.hasMetadata(InventoryA.INVENTORY_OPTION_PREFIX + InventoryOptions.CLICK_CANCEL)) {
//            event.setCancelled(true);
//        }
    }

    @EventHandler
    public void onInventoryClose(InventoryCloseEvent event) {
        Inventory inventory = event.getInventory();
        Player player = (Player) event.getPlayer();
//        for (InventoryOptions inventoryOptions : InventoryOptions.values()) {
//            player.removeMetadata(InventoryA.INVENTORY_OPTION_PREFIX + inventoryOptions, Lib.getLib());
//        }
        InventoryA.player_inventoryOptions.removeValues(player.getUniqueId());
        InventoryA.player_inventoryKeyMap.remove(player.getUniqueId());
    }
}
