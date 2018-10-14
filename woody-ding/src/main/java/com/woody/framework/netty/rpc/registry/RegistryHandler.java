package com.woody.framework.netty.rpc.registry;

import com.woody.framework.netty.rpc.core.msg.InvokerMsg;
import io.netty.channel.ChannelHandlerContext;
import io.netty.channel.ChannelInboundHandlerAdapter;

import java.io.File;
import java.lang.reflect.Method;
import java.net.URL;
import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.ConcurrentHashMap;

//处理整个注册中心的业务逻辑
public class RegistryHandler extends ChannelInboundHandlerAdapter {

    //在注册中心注册的服务需要有一个容器存放
    public static ConcurrentHashMap<String, Object> registryMap = new ConcurrentHashMap<String, Object>();

    private List<String> classCache = new ArrayList<String>();

    //约定，只要写在provider包下面的所有类都认为是一个可以对外提供服务的实现类
    public RegistryHandler() {
        //扫描服务提供实现类
        scannerClass("com.woody.framework.netty.rpc.provider");
        //注册服务提供实现类到容器
        doRegister();
    }


    @Override
    public void channelRead(ChannelHandlerContext ctx, Object msg) throws Exception {
        Object result = new Object();
        //客户端传递过来的调用信息
        InvokerMsg request = (InvokerMsg) msg;

        if (registryMap.containsKey(request.getClassName())) {
            Object clazz = registryMap.get(request.getClassName());
            Method method = clazz.getClass().getMethod(request.getMethodName(), request.getParames());
            result = method.invoke(clazz, request.getValues());
        }
        ctx.write(result);
        ctx.flush();
        ctx.close();
    }

    @Override
    public void exceptionCaught(ChannelHandlerContext ctx, Throwable cause) throws Exception {
        cause.printStackTrace();
        ctx.close();
    }


    //IOC容器（大概）
    //扫描所有Class
    private void scannerClass(String packageName) {
        URL url = this.getClass().getClassLoader().getResource(packageName.replaceAll("\\.", "/"));
        File dir = new File(url.getFile());
        for (File file : dir.listFiles()) {
            //如果是一个文件夹，继续递归
            if (file.isDirectory()) {
                scannerClass(packageName + "." + file.getName());
            } else {
                classCache.add(packageName + "." + file.getName().replace(".class", "").trim());
            }
        }
    }

    //把扫描到class实例化，放到map中，这就是注册过程
    //注册的服务名字，叫接口名字
    //约定优于配置
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

}
