package com.woody.framework.gptomcat.servlets;

import com.alibaba.fastjson.JSON;
import com.woody.framework.gptomcat.http.GPRequest;
import com.woody.framework.gptomcat.http.GPResponse;
import com.woody.framework.gptomcat.http.GPServlet;

public class SecondServlet extends GPServlet {

    @Override
    public void doGet(GPRequest request, GPResponse response) {
        doPost(request, response);
    }

    @Override
    public void doPost(GPRequest request, GPResponse response) {
        String str = JSON.toJSONString(request.getParameters(),true);
        response.write(str,200);
    }

}
