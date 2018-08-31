package com.woody.framework.clone.demo;

import java.io.Serializable;

public class Teacher implements Serializable {

    private static final long serialVersionUID = 8379416724493545501L;

    private String name;
    private String className;

    public Teacher(String name, String className) {
        this.name = name;
        this.className = className;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getClassName() {
        return className;
    }

    public void setClassName(String className) {
        this.className = className;
    }

    @Override
    public String toString() {
        return "Teacher{" +
                "name='" + name + '\'' +
                ", className='" + className + '\'' +
                '}';
    }
}
