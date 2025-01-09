package org.ajls.lib;

import org.ajls.lib.command.D;
import org.ajls.lib.command.Rl;
import org.ajls.lib.overhaul.CombatO;
import org.ajls.lib.references.Time;
import org.bukkit.plugin.java.JavaPlugin;

public final class Lib extends JavaPlugin {
    static Lib plugin;
    public static Lib getLib() {return plugin;}

    @Override
    public void onEnable() {
        // Plugin startup logic
        plugin = this;
        getServer().getPluginManager().registerEvents(new CombatO(), this);
        getCommand("d").setExecutor(new D());
        getCommand("rl").setExecutor(new Rl());

        Time.startTimer();

    }

    @Override
    public void onDisable() {
        // Plugin shutdown logic
    }
}
