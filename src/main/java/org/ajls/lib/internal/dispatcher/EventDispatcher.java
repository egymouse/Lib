package org.ajls.lib.internal.dispatcher;

import org.ajls.lib.api.PriorityEventHandler;
import org.bukkit.event.Cancellable;
import org.bukkit.event.Event;

import java.lang.reflect.Method;
import java.util.*;

public class EventDispatcher {

    // 存储：事件类型 -> 该事件对应的所有处理器列表（已按优先级排序）
    private final Map<Class<? extends Event>, List<HandlerMethod>> handlerMap = new HashMap<>();

    /**
     * 注册一个监听器对象，扫描其中的所有方法。
     */
    public void registerListener(Object listener) {
        Class<?> clazz = listener.getClass();
        for (Method method : clazz.getDeclaredMethods()) {
            // 检查是否有 @MyEventHandler 注解
            PriorityEventHandler anno =
                    method.getAnnotation(PriorityEventHandler.class);
            if (anno == null) continue;

            // 验证方法签名：必须只有一个参数，且参数类型是 Event 的子类
            Class<?>[] paramTypes = method.getParameterTypes();
            if (paramTypes.length != 1 || !Event.class.isAssignableFrom(paramTypes[0])) {
                continue; // 不符合要求，跳过
            }
            @SuppressWarnings("unchecked")
            Class<? extends Event> eventType = (Class<? extends Event>) paramTypes[0];

            // 构造处理器
            HandlerMethod handler = new HandlerMethod(
                    listener,
                    method,
                    anno.priority(),
                    anno.ignoreCancelled()
            );

            // 添加到对应事件类型的列表
            handlerMap.computeIfAbsent(eventType, k -> new ArrayList<>()).add(handler);
        }

        // 对所有事件类型的列表按优先级排序（升序）
        for (List<HandlerMethod> list : handlerMap.values()) {
            list.sort(Comparator.comparingInt(HandlerMethod::getPriority));
        }
    }

    /**
     * 分发事件：按顺序调用该事件类型的所有处理器。
     * 注意：如果某个处理器修改了事件的取消状态，后续处理器可根据 ignoreCancelled 决定是否执行。
     */
    public void dispatch(Event event) {
        List<HandlerMethod> handlers = handlerMap.get(event.getClass());
        if (handlers == null) return;

        // 先判断该事件是否支持取消（因为不是所有事件都能被取消）
        boolean cancellable = event instanceof Cancellable;

        for (HandlerMethod handler : handlers) {
            // 如果方法设置了 ignoreCancelled=false，且事件可取消且已被取消，则跳过
            if (!handler.isIgnoreCancelled() && cancellable && ((Cancellable) event).isCancelled()) {
                continue;
            }
            handler.invoke(event);
        }
    }

    /**
     * 获取当前已注册的所有事件类型（供 Binder 使用）
     */
    public Set<Class<? extends Event>> getEventTypes() {
        return handlerMap.keySet();
    }
}
