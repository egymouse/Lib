package org.ajls.lib.advanced.customevents;

import org.bukkit.event.inventory.ClickType;
import org.bukkit.event.inventory.InventoryAction;
import org.bukkit.event.inventory.InventoryClickEvent;
import org.bukkit.event.inventory.InventoryType;
import org.bukkit.inventory.InventoryView;
import org.jetbrains.annotations.NotNull;

public class InventoryClickNotAirEvent extends InventoryClickEvent {

    public InventoryClickNotAirEvent(@NotNull InventoryView view, @NotNull InventoryType.SlotType type, int slot, @NotNull ClickType click, @NotNull InventoryAction action) {
        super(view, type, slot, click, action);
    }

    public InventoryClickNotAirEvent(@NotNull InventoryView view, @NotNull InventoryType.SlotType type, int slot, @NotNull ClickType click, @NotNull InventoryAction action, int key) {
        super(view, type, slot, click, action, key);
    }
}
