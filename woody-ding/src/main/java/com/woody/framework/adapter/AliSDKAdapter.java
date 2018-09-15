package com.woody.framework.adapter;

public class AliSDKAdapter implements CloudSDK {

    private AliSDK aliSDK;
    public AliSDKAdapter(AliSDK aliSDK) {
        this.aliSDK = aliSDK;
    }

    @Override
    public void putObject(String fileName) {
        aliSDK.setBucket();
        aliSDK.uploadFile(fileName);
    }
}
