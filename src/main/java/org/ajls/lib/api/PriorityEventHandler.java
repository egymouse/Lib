package org.ajls.lib.api;

import org.bukkit.event.EventPriority;

import java.lang.annotation.ElementType;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.lang.annotation.Target;

@Retention(RetentionPolicy.RUNTIME)
@Target(ElementType.METHOD)
public @interface PriorityEventHandler {
    int priority() default 0;
    boolean ignoreCancelled() default false;
    // ★ 新增：Bukkit 优先级，每个方法可单独指定
    EventPriority bukkitPriority() default EventPriority.NORMAL;
}