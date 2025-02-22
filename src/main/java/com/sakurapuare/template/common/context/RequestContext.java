package com.sakurapuare.template.common.context;

import com.sakurapuare.template.common.RequestInfo;

public class RequestContext {
    private static final ThreadLocal<RequestInfo> contextHolder = new ThreadLocal<>();

    public static RequestInfo getContext() {
        return contextHolder.get();
    }

    public static void setContext(RequestInfo requestInfo) {
        contextHolder.set(requestInfo);
    }

    public static void clear() {
        contextHolder.remove();
    }
}
