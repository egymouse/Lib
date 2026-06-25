package org.ajls.lib.internal.dispatcher;

import org.ajls.lib.api.PriorityEventHandler;
import org.bukkit.event.Cancellable;
import org.bukkit.event.Event;
import org.bukkit.event.EventPriority;

import java.lang.reflect.Method;
import java.util.*;

public class EventDispatcher {

    // 存储：事件类型 -> 该事件对应的所有处理器列表（已按优先级排序）
//    private final Map<Class<? extends Event>, List<HandlerMethod>> handlerMap = new HashMap<>();
    // 改为：
    private final Map<Class<? extends Event>, Map<EventPriority, List<HandlerMethod>>> handlerMap = new HashMap<>();

    /**
     * 注册一个监听器对象，扫描其中的所有方法。
     */
    public void registerListener(Object listener) {
        Class<?> clazz = listener.getClass();
        for (Method method : clazz.getDeclaredMethods()) {
            // 检查是否有 @MyEventHandler 注解
            PriorityEventHandler anno = method.getAnnotation(PriorityEventHandler.class);
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
                    anno.ignoreCancelled(),
                    anno.bukkitPriority()   // ★ 传入 Bukkit 优先级
            );

            // ★ 先获取事件类型的 Map<EventPriority, List<HandlerMethod>>
            Map<EventPriority, List<HandlerMethod>> priorityMap =
                    handlerMap.computeIfAbsent(eventType, k -> new HashMap<>());
            // 再获取对应优先级的列表
            List<HandlerMethod> list = priorityMap.computeIfAbsent(anno.bukkitPriority(), k -> new ArrayList<>());
            list.add(handler);
        }

        // ★ 排序：对每个事件类型下的每个优先级列表，按数字 priority 排序
        for (Map<EventPriority, List<HandlerMethod>> priorityMap : handlerMap.values()) {
            for (List<HandlerMethod> list : priorityMap.values()) {
                list.sort(Comparator.comparingInt(HandlerMethod::getPriority));
            }
        }

    }

//    /**
//     * 分发事件：按顺序调用该事件类型的所有处理器。
//     * 注意：如果某个处理器修改了事件的取消状态，后续处理器可根据 ignoreCancelled 决定是否执行。
//     */
//    public void dispatch(Event event) {
//        List<HandlerMethod> handlers = handlerMap.get(event.getClass());
//        if (handlers == null) return;
//
//        // 先判断该事件是否支持取消（因为不是所有事件都能被取消）
//        boolean cancellable = event instanceof Cancellable;
//
//        for (HandlerMethod handler : handlers) {
//            // 如果方法设置了 ignoreCancelled=false，且事件可取消且已被取消，则跳过
//            if (!handler.isIgnoreCancelled() && cancellable && ((Cancellable) event).isCancelled()) {
//                continue;
//            }
//            handler.invoke(event);
//        }
//    }

    /**
     * 分发事件给指定 Bukkit 优先级的方法列表
     */
    public void dispatchFor(Event event, EventPriority bukkitPriority) {
//        List<HandlerMethod> handlers = findHandlers(event.getClass(), bukkitPriority);

        Map<EventPriority, List<HandlerMethod>> priorityMap = handlerMap.get(event.getClass());
        if (priorityMap == null) return;

        List<HandlerMethod> handlers = priorityMap.get(bukkitPriority);


        if (handlers == null) return;

        boolean cancellable = event instanceof Cancellable;
        for (HandlerMethod handler : handlers) {
            if (!handler.isIgnoreCancelled() && cancellable && ((Cancellable) event).isCancelled()) {
                continue;
            }
            handler.invoke(event);
        }
    }


    /**
     * 根据事件类型和 Bukkit 优先级查找对应的处理器列表。
     * 若精确匹配没有，则向上遍历父类（继承链）查找。
     * 返回最先找到的（距离子类最近的）父类的处理器。
     */
    private List<HandlerMethod> findHandlers(Class<?> eventClass, EventPriority bukkitPriority) {
        // 1. 精确匹配
        Map<EventPriority, List<HandlerMethod>> priorityMap = handlerMap.get(eventClass);
        if (priorityMap != null) {
            List<HandlerMethod> list = priorityMap.get(bukkitPriority);
            if (list != null && !list.isEmpty()) {
                return list;
            }
        }
        // 2. 向上遍历父类（直到 Event 为止）
        Class<?> superClass = eventClass.getSuperclass();
        while (superClass != null && Event.class.isAssignableFrom(superClass)) {
            priorityMap = handlerMap.get(superClass);
            if (priorityMap != null) {
                List<HandlerMethod> list = priorityMap.get(bukkitPriority);
                if (list != null && !list.isEmpty()) {  //todo 这只是返回最近的，希望返回所有，而且最好是源头就解决，源头registerEvent就要老老实实子类也触发父类
                    return list;
                }
            }
            superClass = superClass.getSuperclass();
        }
        return null;
    }

    public Map<EventPriority, List<HandlerMethod>> getHandlerMapFor(Class<? extends Event> eventType) {
        return handlerMap.get(eventType);
    }

    /**
     * 获取当前已注册的所有事件类型（供 Binder 使用）
     */
    public Set<Class<? extends Event>> getEventTypes() {
        return handlerMap.keySet();
    }
}
