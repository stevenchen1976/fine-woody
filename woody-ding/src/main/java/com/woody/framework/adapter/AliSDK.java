package com.woody.framework.adapter;

public class AliSDK {

    public void setBucket(){
        System.out.println("Hi, Ali oss first set bucket");
    }

    public void uploadFile(String fileName) {
        System.out.println("Hi, Ali upload oss file " + fileName);
    }
}
