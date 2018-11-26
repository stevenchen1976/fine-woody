package com.woody.framework.ws.cxfspring.server;

import com.woody.framework.ws.cxfspring.server.autowired.UserService;

import javax.jws.WebService;

@WebService(endpointInterface = "com.woody.framework.ws.cxfspring.server.HelloWebService",
        serviceName = "HelloWorldWs")
public class HelloWebServiceImpl implements HelloWebService {

    private UserService userService;

    public void setUserService(UserService userService) {
        this.userService = userService;
    }

    @Override
    public String sayHello(String name) {
        return "Hello" + name + "123" + userService.getUserName("woodyfine");
    }
}
