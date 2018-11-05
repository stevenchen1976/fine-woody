package com.woody.framework.test.rpc.provider;

import com.woody.framework.test.rpc.api.IMyName;

public class MyName implements IMyName {

    @Override
    public String myName(String name) {
        return "Hi, My name is " + name;
    }
}
