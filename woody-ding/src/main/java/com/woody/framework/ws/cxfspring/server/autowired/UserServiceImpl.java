package com.woody.framework.ws.cxfspring.server.autowired;

public class UserServiceImpl implements UserService {
    @Override
    public String getUserName(String name) {
        return "username" + name;
    }
}
