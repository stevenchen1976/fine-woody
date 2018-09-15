package com.woody.framework.adapter;

public class CloudController {

    CloudService cloudService;

    public CloudController(CloudService cloudService) {
        this.cloudService = cloudService;
    }

    public void storeFileToCloud() {
        cloudService.uploadFile("Test.zip");
    }

    public static void main(String[] args){
        CloudController cloudController = new CloudController(new CloudService("AWSSDK"));
        cloudController.storeFileToCloud();
    }
}
