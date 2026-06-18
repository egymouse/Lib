package org.ajls.lib.api;

import org.ajls.lib.Lib;

public class LIbAPI {
    public static void registerListener(Object listener) {
        Lib.getLib().getDispatcher().registerListener(listener);
        // 注册新监听器后，通知Binder刷新（可以延迟或立即）
        Lib.getLib().getBinder().refreshRegistration();
    }
}
