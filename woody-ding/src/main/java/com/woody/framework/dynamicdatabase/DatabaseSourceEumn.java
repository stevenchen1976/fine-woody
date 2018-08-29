package com.woody.framework.dynamicdatabase;

public enum  DatabaseSourceEumn {

    mysql_1("dataSource"),
    mysql_2("dataSource2");

    private String val;

    DatabaseSourceEumn(String val) {
        this.val = val;
    }

    public String getVal() {
        return val;
    }
}
