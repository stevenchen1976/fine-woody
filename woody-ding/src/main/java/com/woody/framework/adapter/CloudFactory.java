package com.woody.framework.adapter;

import java.util.HashMap;
import java.util.Map;

public class CloudFactory {

    static Map<String, CloudSDK> cloudSDKMap = new HashMap<>();

    static {
        cloudSDKMap.put("AliSDK", new AliSDKAdapter(new AliSDK()));
        cloudSDKMap.put("AWSSDK", new AWSSDKAdapter(new AWSSDK()));
    }

    public static CloudSDK create(String cloudStrategy) {

        return cloudSDKMap.get(cloudStrategy);
    }
}
