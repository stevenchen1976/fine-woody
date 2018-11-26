package com.woody.framework.ws.cxfspring.server;

import javax.jws.WebService;

@WebService
public interface HelloWebService {

    String sayHello(String name);
}
