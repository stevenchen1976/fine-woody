package com.woody.framework.adapter;

public class CloudService {

    CloudSDK cloudSDK;

    public CloudService(String cloudStrategy) {
        this.cloudSDK = CloudFactory.create(cloudStrategy);
    }

    public void uploadFile(String fileName) {
        cloudSDK.putObject(fileName);
    }

}
