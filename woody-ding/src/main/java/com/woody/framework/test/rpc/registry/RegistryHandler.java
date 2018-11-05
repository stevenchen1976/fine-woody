package com.woody.framework.test.rpc.registry;

import com.woody.framework.test.rpc.core.InvokeMsg;
import io.netty.channel.ChannelHandlerContext;
import io.netty.channel.ChannelInboundHandlerAdapter;

import java.io.File;
import java.lang.reflect.Method;
import java.net.URL;
import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.ConcurrentHashMap;

public class RegistryHandler extends ChannelInboundHandlerAdapter {

    //服务容器
    public static ConcurrentHashMap<String, Object> registryMap = new ConcurrentHashMap<>();
    //服务缓存
    public static List<String> serviceCacheList = new ArrayList<>();


    public
    RegistryHandler() throws IllegalAccessException, InstantiationException, ClassNotFoundException {
        //扫描服务
        scanService("com.woody.framework.test.rpc.provider");
        //注册服务
        doRegistry();
    }

    @Override
    public void channelRead(ChannelHandlerContext ctx, Object msg) throws Exception {

        Object result = new Object();

        InvokeMsg request = (InvokeMsg) msg;

        if (registryMap.containsKey(request.getClassName())) {
            Object clazz = registryMap.get(request.getClassName());
            Method method = clazz.getClass().getMethod(request.getMethodName(), request.getParam());
            result = method.invoke(clazz, request.getValue());
        }
        ctx.channel().writeAndFlush(result);
        ctx.close();
    }

    @Override
    public void exceptionCaught(ChannelHandlerContext ctx, Throwable cause) throws Exception {
        cause.printStackTrace();
        ctx.close();
    }

    //扫描服务
    private void scanService(String packages) {
        URL url = this.getClass().getClassLoader().getResource(packages.replaceAll("\\.", "/"));
        File dir = new File(url.getFile());
        for (File f : dir.listFiles()) {
            if (f.isDirectory()) {
                scanService(packages + "." + f.getName());
            } else {
//                serviceCacheList.add(packages.replace(".class", ""));
                serviceCacheList.add(packages + "." + f.getName().replace(".class", "").trim());
            }
        }
    }

    //注册服务
    private void doRegistry() throws ClassNotFoundException, IllegalAccessException, InstantiationException {
        if (serviceCacheList.size() < 1) {
            return;
        }
        for (String className : serviceCacheList) {
            try {
                Class<?> clazz = Class.forName(className);
                Class<?> interfaces = clazz.getInterfaces()[0];

                registryMap.put(interfaces.getName(), clazz.newInstance());
            } catch (Exception e) {
                e.printStackTrace();
            }

        }
    }

}
