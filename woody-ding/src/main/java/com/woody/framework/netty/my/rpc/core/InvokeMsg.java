package com.woody.framework.netty.my.rpc.core;

import java.io.Serializable;

public class InvokeMsg implements Serializable {

    private static final long serialVersionUID = -7492616693333063560L;

    private String className;    //类名
    private String methodName;  //函数名称
    private Class<?>[] params;  //参数类型
    private Object[] value;  //参数列表

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

    public Class<?>[] getParams() {
        return params;
    }

    public void setParams(Class<?>[] params) {
        this.params = params;
    }

    public Object[] getValue() {
        return value;
    }

    public void setValue(Object[] value) {
        this.value = value;
    }
}
