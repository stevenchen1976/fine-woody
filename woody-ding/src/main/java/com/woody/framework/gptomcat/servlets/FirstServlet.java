package com.woody.framework.gptomcat.servlets;

import com.woody.framework.gptomcat.http.GPRequest;
import com.woody.framework.gptomcat.http.GPResponse;
import com.woody.framework.gptomcat.http.GPServlet;

public class FirstServlet extends GPServlet {


    @Override
    public void doGet(GPRequest request, GPResponse respose) {
        doPost(request, respose);
    }

    @Override
    public void doPost(GPRequest request, GPResponse response) {
        String param = "name";
        String str = request.getParameter(param);
        response.write(param + ":" + str,200);
    }
}
