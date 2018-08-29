package com.woody.framework.utils;

public class StringUtil {

    public static boolean isNullOrBlank(String value) {
        return value == null || "".equals(value.trim());
    }
}
