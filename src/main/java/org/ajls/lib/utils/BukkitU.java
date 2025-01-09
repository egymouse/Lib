package org.ajls.lib.utils;

import org.bukkit.Bukkit;
import org.bukkit.event.Event;
import org.bukkit.event.Listener;
import org.bukkit.plugin.Plugin;
import org.bukkit.scheduler.BukkitScheduler;

public class BukkitU {
    public static void callEvent(Event event) {
        Bukkit.getServer().getPluginManager().callEvent(event);
    }

//    public static void callEventAsync(Event event) {
//        Bukkit.getServer().getPluginManager().callEvent(event);
//    }

    public static void registerEvents(Listener listener, Plugin plugin) {
//        Bukkit.getServer().getPluginManager().registerEvents(listener, plugin);
        plugin.getServer().getPluginManager().registerEvents(listener, plugin);

    }

    public static BukkitScheduler getScheduler() {
        return Bukkit.getServer().getScheduler();
    }
}
