package com.woody.framework.utils;


public class EnumUtils {
    public static <T extends Enum & BaseEnum> T getEnum(Class<T> clazz, Object val) {
        if (clazz == null || !clazz.isEnum() || val == null) {
            return null;
        }
        for (T t : clazz.getEnumConstants()) {
            if (t.getValue().equals(val.toString())) {
                return t;
            }
        }
        return null;
    }
}
