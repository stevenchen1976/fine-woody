package com.woody.framework.test.rpc.consumer;

import com.woody.framework.test.rpc.api.IMyName;

public class ServiceConsumer {

    public static void main(String[] args){
        IMyName service = RPCProxy.create(IMyName.class);
        System.out.println(service.myName("Woodyfine"));
    }
}
