package org.ajls.lib.internal.registry;


import org.ajls.lib.internal.dispatcher.EventDispatcher;
import org.ajls.lib.internal.dispatcher.HandlerMethod;
import org.bukkit.event.Event;
import org.bukkit.event.EventPriority;
import org.bukkit.event.Listener;
import org.bukkit.plugin.PluginManager;
import org.bukkit.plugin.java.JavaPlugin;

import java.util.HashSet;
import java.util.List;
import java.util.Map;
import java.util.Set;

public class BukkitEventBinder {
    private final JavaPlugin plugin;
    private final EventDispatcher dispatcher;
    private final Set<String> registeredTypes = new HashSet<>();

    public BukkitEventBinder(JavaPlugin plugin, EventDispatcher dispatcher) {
        this.plugin = plugin;
        this.dispatcher = dispatcher;
    }

    public void refreshRegistration() {
//        // 获取dispatcher中所有事件类型，对比registeredTypes，对新类型调用registerEvent
//        for (Class<? extends Event> eventType : dispatcher.getEventTypes()) { // 需要在dispatcher暴露getEventTypes
//            if (!registeredTypes.contains(eventType)) {
//                plugin.getServer().getPluginManager().registerEvent(
//                    eventType,
//                    new Listener() {},
//                    EventPriority.LOWEST,
//                    (listener, event) -> dispatcher.dispatch(event),
//                    plugin
//                );
//                registeredTypes.add(eventType);
//            }
//        }


        PluginManager pm = plugin.getServer().getPluginManager();
        // 获取所有事件类型
        Set<Class<? extends Event>> eventTypes = dispatcher.getEventTypes();

        for (Class<? extends Event> eventType : eventTypes) {
            // 获取该事件类型对应的 Bukkit优先级 -> 方法列表 映射
            Map<EventPriority, List<HandlerMethod>> priorityMap = dispatcher.getHandlerMapFor(eventType);
            // ↑ 需要在 EventDispatcher 中增加一个公开方法 getHandlerMapFor(Class)

            if (priorityMap == null) continue;

            // 遍历每个 Bukkit 优先级
            for (EventPriority bukkitPriority : priorityMap.keySet()) {
                // 为避免重复注册，可以记录已经注册过的组合 (eventType, bukkitPriority)
                // 简单起见，我们只注册一次，用一个 Set 记录
                String key = eventType.getName() + "#" + bukkitPriority.name();
                if (registeredTypes.contains(key)) continue;

                pm.registerEvent(
                        eventType,
                        new Listener() {},
                        bukkitPriority,   // ★ 使用方法指定的优先级
                        (listener, event) -> dispatcher.dispatchFor(event, bukkitPriority), // ★ 只执行该优先级的方法
                        plugin
                );
                registeredTypes.add(key);
                plugin.getLogger().info("已为事件 " + eventType.getSimpleName() +
                        " 注册 Bukkit优先级=" + bukkitPriority.name() + " 的监听器");
            }
        }
    }


}