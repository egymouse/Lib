package org.ajls.lib.internal.registry;


import org.ajls.lib.internal.dispatcher.EventDispatcher;
import org.bukkit.event.Event;
import org.bukkit.event.EventPriority;
import org.bukkit.event.Listener;
import org.bukkit.plugin.java.JavaPlugin;

import java.util.HashSet;
import java.util.Set;

public class BukkitEventBinder {
    private final JavaPlugin plugin;
    private final EventDispatcher dispatcher;
    private final Set<Class<? extends Event>> registeredTypes = new HashSet<>();

    public BukkitEventBinder(JavaPlugin plugin, EventDispatcher dispatcher) {
        this.plugin = plugin;
        this.dispatcher = dispatcher;
    }

    public void refreshRegistration() {
        // 获取dispatcher中所有事件类型，对比registeredTypes，对新类型调用registerEvent
        for (Class<? extends Event> eventType : dispatcher.getEventTypes()) { // 需要在dispatcher暴露getEventTypes
            if (!registeredTypes.contains(eventType)) {
                plugin.getServer().getPluginManager().registerEvent(
                    eventType,
                    new Listener() {},
                    EventPriority.LOWEST,
                    (listener, event) -> dispatcher.dispatch(event),
                    plugin
                );
                registeredTypes.add(eventType);
            }
        }
    }
}