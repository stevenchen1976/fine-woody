package com.woody.framework.netty.my.rpc.registry;

import com.woody.framework.netty.my.rpc.core.InvokeMsg;
import io.netty.channel.ChannelHandlerContext;
import io.netty.channel.ChannelInboundHandlerAdapter;

import java.io.File;
import java.lang.reflect.Method;
import java.net.URL;
import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.ConcurrentHashMap;

public class RegistryHandler extends ChannelInboundHandlerAdapter {

    //注册中心容器
    public static ConcurrentHashMap<String, Object> registryMap = new ConcurrentHashMap<>();
    //缓存
    private List<String> classCache = new ArrayList<>();

    public RegistryHandler() {
        //扫描服务提供类
        scanerClass("com.woody.framework.netty.my.rpc.provider");
        //注册服务提供类到注册中心容器
        doRegister();
    }

    @Override
    public void channelRead(ChannelHandlerContext ctx, Object msg) throws Exception {
        Object result = new Object();

        InvokeMsg request = (InvokeMsg) msg;

        if (registryMap.containsKey(request.getClassName())) {
            Object clazz = registryMap.get(request.getClassName());
            Method method = clazz.getClass().getMethod(request.getMethodName(), request.getParams());
            result = method.invoke(clazz, request.getValue());
        }

        ctx.writeAndFlush(result);
        ctx.close();
    }


    private void scanerClass(String packageName) {
        URL url = this.getClass().getClassLoader().getResource(packageName.replaceAll("\\.", "/"));
        File dir = new File(url.getFile());
        for (File file : dir.listFiles()) {
            //如果是个文件夹，则继续递归
            if (file.isDirectory()) {
                scanerClass(packageName + "." + file.getName());
            } else {
                classCache.add(packageName + "." + file.getName().replace(".class", "").trim());
            }
        }

    }

    private void doRegister() {
        if (classCache.size() == 0) {
            return;
        }
        for (String className : classCache) {
            try {
                Class<?> clazz = Class.forName(className);
                Class<?> interfaces = clazz.getInterfaces()[0];
                registryMap.put(interfaces.getName(), clazz.newInstance());
            } catch (Exception e) {
                e.printStackTrace();
            }
        }
    }

    @Override
    public void exceptionCaught(ChannelHandlerContext ctx, Throwable cause) throws Exception {

        cause.printStackTrace();
        ctx.close();
    }
}
