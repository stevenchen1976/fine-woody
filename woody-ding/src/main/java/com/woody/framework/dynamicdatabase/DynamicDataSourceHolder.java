package com.woody.framework.dynamicdatabase;


public class DynamicDataSourceHolder {

    private static ThreadLocal<String> databaseSourceKey = new ThreadLocal<>();

    //绑定当前线程数据源的路由key，使用后必须删除
    public static void setRouteKey(String key) {
        databaseSourceKey.set(key);
    }

    //获取当前线程数据源路由key
    public static String getRouteKey() {
        return databaseSourceKey.get();
    }

    //删除当前数据源路由key
    public static void removeRouteKey() {
        databaseSourceKey.remove();
    }

}
