package org.ajls.lib.listeners;

import org.ajls.lib.utils.ItemStackU;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.inventory.ItemStack;

public class GeneralListener implements Listener {

    @EventHandler
    public void onPlayerPickupItem(org.bukkit.event.player.PlayerPickupItemEvent event) {
        // Example: Prevent players from picking up items with a specific name
        if (event.getItem().getItemStack().getItemMeta() != null &&
            event.getItem().getItemStack().getItemMeta().hasDisplayName() &&
            event.getItem().getItemStack().getItemMeta().getDisplayName().equals("No Pickup Item")) {
            event.setCancelled(true);
        }
        ItemStack itemStack = event.getItem().getItemStack();
        if (ItemStackU.isNotDroppable(itemStack)) {
            event.setCancelled(true);
        }
    }
}
