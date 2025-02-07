package org.ajls.lib.advanced.inventory;

import org.bukkit.event.inventory.ClickType;
import org.bukkit.event.inventory.InventoryAction;
import org.bukkit.event.inventory.InventoryClickEvent;
import org.bukkit.event.inventory.InventoryType;
import org.bukkit.inventory.InventoryView;
import org.jetbrains.annotations.NotNull;

public class InventoryClickEventA extends InventoryClickEvent {
    public boolean isAir = false;

    public InventoryClickEventA(@NotNull InventoryView view, @NotNull InventoryType.SlotType type, int slot, @NotNull ClickType click, @NotNull InventoryAction action, int key) {
        super(view, type, slot, click, action, key);
    }

    public InventoryClickEventA(@NotNull InventoryView view, @NotNull InventoryType.SlotType type, int slot, @NotNull ClickType click, @NotNull InventoryAction action) {
        super(view, type, slot, click, action);
    }

//    public static InventoryClickEventA fromInventoryClickEvent(@NotNull InventoryClickEvent event) {
//        InventoryView view = event.getView();
//        InventoryType type = view.getType();
//        int slot = event.getSlot();
//        ClickType click = event.getClick();
//        InventoryAction action = event.getAction();
//        int key = event.
//    }

    public void setAir(boolean isAir) {
        this.isAir = isAir;
    }

    public boolean isAir() {
        return isAir;
    }


}
