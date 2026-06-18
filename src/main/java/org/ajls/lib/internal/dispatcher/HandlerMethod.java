package org.ajls.lib.internal.dispatcher;

import org.bukkit.event.Event;
import org.bukkit.event.EventPriority;

import java.lang.reflect.Method;

/**
 * 封装一个被 @MyEventHandler 标记的方法，包含其优先级和忽略取消标志。
 */
public class HandlerMethod {
    private final Object listener;      // 方法所属的监听器实例
    private final Method method;        // 方法反射对象
    private final int priority;
    private final boolean ignoreCancelled;
    private final EventPriority bukkitPriority;   // ★ 新增

    HandlerMethod(Object listener, Method method, int priority,
                  boolean ignoreCancelled, EventPriority bukkitPriority) {  // 构造参数增加
        this.listener = listener;
        this.method = method;
        this.priority = priority;
        this.ignoreCancelled = ignoreCancelled;
        this.bukkitPriority = bukkitPriority;    // ★ 赋值
        method.setAccessible(true);
    }

    // 增加 getter
    public EventPriority getBukkitPriority() { return bukkitPriority; }

    public int getPriority() {
        return priority;
    }

    public boolean isIgnoreCancelled() {
        return ignoreCancelled;
    }

    /**
     * 执行此方法，传入事件对象
     */
    public void invoke(Event event) {
        try {
            method.invoke(listener, event);
        } catch (Exception e) {
            e.printStackTrace();
            // 可以添加日志
        }
    }
}