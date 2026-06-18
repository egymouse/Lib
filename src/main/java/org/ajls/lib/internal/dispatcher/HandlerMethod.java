package org.ajls.lib.internal.dispatcher;

import org.bukkit.event.Event;

import java.lang.reflect.Method;

/**
 * 封装一个被 @MyEventHandler 标记的方法，包含其优先级和忽略取消标志。
 */
class HandlerMethod {
    private final Object listener;      // 方法所属的监听器实例
    private final Method method;        // 方法反射对象
    private final int priority;
    private final boolean ignoreCancelled;

    HandlerMethod(Object listener, Method method, int priority, boolean ignoreCancelled) {
        this.listener = listener;
        this.method = method;
        this.priority = priority;
        this.ignoreCancelled = ignoreCancelled;
        // 确保方法可访问（即使是private）
        method.setAccessible(true);
    }

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