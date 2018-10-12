package com.woody.framework.gptomcat.nettyserver;

import com.woody.framework.gptomcat.http.GPRequest;
import com.woody.framework.gptomcat.http.GPResponse;
import com.woody.framework.gptomcat.http.GPServlet;
import com.woody.framework.utils.CustomConfig;
import io.netty.channel.ChannelHandlerContext;
import io.netty.channel.ChannelInboundHandlerAdapter;
import io.netty.handler.codec.http.HttpRequest;
import org.apache.log4j.Logger;
import org.springframework.util.StringUtils;

import java.util.HashMap;
import java.util.Map;
import java.util.regex.Pattern;

/**
 * Netty中的所有handler都实现自ChannelHandler接口。
 * 按照输出输出来分，分为ChannelInboundHandler、ChannelOutboundHandler两大类。
 * ChannelInboundHandler对从客户端发往服务器的报文进行处理，一般用来执行解码、读取客户端数据、进行业务处理等；
 * ChannelOutboundHandler对从服务器发往客户端的报文进行处理，一般用来进行编码、发送报文到客户端
 */
public class GPTomcatHandler extends ChannelInboundHandlerAdapter {

    private static final Map<Pattern, Class<?>> servletMapping = new HashMap<>();

    static {
        CustomConfig.load("web.properties");

        for (String key : CustomConfig.getKeys()) {
            if (key.startsWith("servlet.")) {
                String name = key.replaceFirst("servlet.", "");
                if (name.indexOf(".") != -1) {
                    name = name.substring(0, name.indexOf("."));
                } else {
                    continue;
                }
                if(!StringUtils.isEmpty(name)) {
                    String pattern = CustomConfig.getString("servlet." + name + ".urlPattern");
                    pattern = pattern.replaceAll("\\*", ".*");
                    String className = CustomConfig.getString("servlet." + name + ".className");
                    if (!servletMapping.containsKey(pattern)) {
                        try {
                            servletMapping.put(Pattern.compile(pattern), Class.forName(className));
                        } catch (ClassNotFoundException e) {
                            e.printStackTrace();
                        }
                    }
                }
            }
        }
    }

    private Logger LOG = Logger.getLogger(GPTomcatHandler.class);

    @Override
    public void channelRead(ChannelHandlerContext ctx, Object msg) throws Exception {
        if (msg instanceof HttpRequest) {
            HttpRequest r = (HttpRequest) msg;
            GPRequest request = new GPRequest(ctx, r);
            GPResponse response = new GPResponse(ctx, r);

            String uri = request.getUri();
            String method = request.getMethod();

            LOG.info(String.format("Uri:%s method %s", uri, method));
//            response.write(uri + "-" + method, 200);

            boolean hasPattern = false;
            for (Map.Entry<Pattern, Class<?>> entry : servletMapping.entrySet()) {
                if (entry.getKey().matcher(uri).matches()) {
                    GPServlet servlet = (GPServlet) entry.getValue().newInstance();
                    if ("get".equalsIgnoreCase(method)) {
                        servlet.doGet(request, response);
                    } else {
                        servlet.doPost(request, response);
                    }
                    hasPattern = true;
                }
            }
            if (!hasPattern) {
                String out = String.format("404 NotFound URL%s for method %s", uri, method);
                response.write(out, 404);
                return;
            }
        }

    }

    @Override
    public void channelReadComplete(ChannelHandlerContext ctx) throws Exception {
        ctx.flush();
    }

    @Override
    public void exceptionCaught(ChannelHandlerContext ctx, Throwable cause) throws Exception {
        cause.printStackTrace();
        ctx.close();
    }
}
