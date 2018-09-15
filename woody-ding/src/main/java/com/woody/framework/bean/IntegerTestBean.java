package com.woody.framework.bean;

import scala.Int;

import java.lang.reflect.Field;

public class IntegerTestBean {

    public static void main(String[] args) throws NoSuchFieldException, IllegalAccessException {

        Integer i1 = 1, i2 =2;
        //自动装箱  Integer a = 1 => Integer a = Integer.valueOf(1);
        // -128-127缓存，提前分配好地址
        System.out.println("Before: i1 = " + i1 + ", i2 = " + i2);
        swap(i1, i2);
        System.out.println("After: i1 = " + i1 + ", i2 = " + i2);
    }

    private static void swap(Integer i1, Integer i2) throws NoSuchFieldException, IllegalAccessException {

        Field field = Integer.class.getDeclaredField("value");
        field.setAccessible(true);  //绕过安全检查
        int tmp = i1.intValue();
        field.setInt(i1, i2.intValue());
        field.setInt(i2, tmp);
    }

}
