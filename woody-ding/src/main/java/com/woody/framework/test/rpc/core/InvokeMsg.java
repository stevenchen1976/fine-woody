package com.woody.framework.test.rpc.core;

import java.io.Serializable;

public class InvokeMsg implements Serializable {

    private String className;
    private String methodName;
    private Class<?>[] param;
//    private Class<?>[] value;
    private Object[] value;

    public String getClassName() {
        return className;
    }

    public void setClassName(String className) {
        this.className = className;
    }

    public String getMethodName() {
        return methodName;
    }

    public void setMethodName(String methodName) {
        this.methodName = methodName;
    }

    public Class<?>[] getParam() {
        return param;
    }

    public void setParam(Class<?>[] param) {
        this.param = param;
    }

    public Object[] getValue() {
        return value;
    }

    public void setValue(Object[] value) {
        this.value = value;
    }
}
