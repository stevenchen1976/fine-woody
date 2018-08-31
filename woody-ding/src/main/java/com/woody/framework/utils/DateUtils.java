package com.woody.framework.utils;

import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;

public class DateUtils extends org.apache.commons.lang.time.DateUtils {

    public final static String FORMAT_DATE_DEFAULT = "yyyy-MM-dd";

    public final static String FORMAT_DATE_YYYYMMDD = "yyyyMMdd";

    public final static String FORMAT_DATE_YYYY_MM_DD = "yyyy-MM-dd";

    public final static String FORMAT_DATE_PATTERN_1 = "yyyy/MM/dd";

    public final static String FORMAT_DATE_PATTERN_2 = "yyyy/M/dd";

    public final static String FORMAT_DATE_PATTERN_3 = "yyyy/MM/d";

    public final static String FORMAT_DATE_PATTERN_4 = "yyyy/M/d";

    public final static String FORMAT_DATE_YYYYMMDDHHMMSS = "yyyyMMddHHmmss";

    public final static String FORMAT_DATE_YYYY_MM_DD_HH_MM_SS = "yyyy-MM-dd HH:mm:ss";

    public final static String FORMAT_DATE_YYYY_MM_DD_HH_MM_SS_AM = "yyyy-MM-dd HH:mm:ss a";

    public final static String FORMAT_DATE_YYYY_MM_DD_HH_MM_SS_SSS = "yyyy-MM-dd HH:mm:ss.SSS";

    public final static String FORMAT_DATE_YYYYMMDDHHMM_SSSSS = "yyyyMMddHHmmssSSS";

    public final static String FORMAT_DATE_YYYY_MM_DD_HHMM = "yyyy-MM-dd HHmm";

    public final static String FORMAT_DATE_YYYY_MM_DD_HH_MM = "yyyy-MM-dd HH:mm";

    public final static String FORMAT_DATE_HH_MM = "HH:mm";

    public final static String FORMAT_DATE_HH_MM_SS = "HH:mm:ss";

    public final static String FORMAT_DATE_HHMM = "HHmm";

    public final static String FORMAT_DATE_HHMMSS = "HHmmss";

    public static final String FORMAT_WORK_TIME = "yyyy-MM-dd HHmmss";

    public static final String FORMAT_DATE_YYMMDD = "yyMMdd";

    public static final String FORMAT_DATE_TIME = "yyyyMMdd";

    /**
     * 获取当前时间
     * @return
     */
    public static Date getCurrentDate() {
        return Calendar.getInstance().getTime();
    }

    /**
     * 返回格式化后日期
     * @param date
     * @param formatPattern
     * @return
     */
    public final static String format(Date date, String formatPattern) {
        if (date == null) {
            return "";
        }
        return new SimpleDateFormat(formatPattern).format(date);
    }
}
