package com.woody.framework.reflect;


import java.lang.reflect.Constructor;
import java.lang.reflect.Field;
import java.lang.reflect.InvocationTargetException;
import java.lang.reflect.Method;

public class Test {

    public static Persom initByDefaultConst() throws ClassNotFoundException, NoSuchMethodException, IllegalAccessException, InvocationTargetException, InstantiationException, NoSuchFieldException {
        ClassLoader classLoader = Thread.currentThread().getContextClassLoader();
        Class<?> clazz = classLoader.loadClass("com.woody.framework.reflect.Persom");
        Constructor<?> cons = clazz.getDeclaredConstructor(null);
        Persom person = (Persom)cons.newInstance();

        Field sex = clazz.getDeclaredField("sex");
        sex.setAccessible(true);
        sex.set(person, 1);

        Method setName = clazz.getMethod("setName", String.class);
        setName.invoke(person, "章三");

        return person;
    }

    public static void main(String[] args) throws ClassNotFoundException, NoSuchMethodException, InvocationTargetException, InstantiationException, IllegalAccessException, NoSuchFieldException {
        Persom person = initByDefaultConst();
        person.saySomething();
    }
}
