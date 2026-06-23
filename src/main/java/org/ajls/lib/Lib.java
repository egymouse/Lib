package org.ajls.lib;

import org.ajls.lib.advanced.inventory.InventoryA;
import org.ajls.lib.advanced.inventory.InventoryListener;
import org.ajls.lib.command.D;
import org.ajls.lib.command.Rl;
import org.ajls.lib.internal.dispatcher.EventDispatcher;
import org.ajls.lib.internal.registry.BukkitEventBinder;
import org.ajls.lib.listeners.GeneralListener;
//import org.ajls.lib.overhaul.CombatO;
import org.ajls.lib.overhaul.CombatO;
import org.ajls.lib.references.Time;
import org.bukkit.plugin.java.JavaPlugin;

public final class Lib extends JavaPlugin {
    static Lib plugin;
    private EventDispatcher dispatcher;
    private BukkitEventBinder binder;
    public static Lib getLib() {return plugin;}

    @Override
    public void onEnable() {
        // Plugin startup logic
        plugin = this;
        dispatcher = new EventDispatcher();
        binder = new BukkitEventBinder(this, dispatcher);

        getServer().getPluginManager().registerEvents(new GeneralListener(), this);
//        getServer().getPluginManager().registerEvents(new CombatO(), this);
        getServer().getPluginManager().registerEvents(new InventoryListener(), this);
        getCommand("d").setExecutor(new D());
        getCommand("rl").setExecutor(new Rl());

        Time.startTimer();

    }

    @Override
    public void onDisable() {
        // Plugin shutdown logic
    }

    public EventDispatcher getDispatcher() {
        return dispatcher;
    }

    public BukkitEventBinder getBinder() {
        return binder;
    }
}
