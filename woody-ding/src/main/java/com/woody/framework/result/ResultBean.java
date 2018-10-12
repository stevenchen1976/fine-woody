package com.woody.framework.result;

import java.io.Serializable;
import java.util.List;

public class ResultBean implements Serializable {

    private static final long serialVersionUID = 5947312831342236002L;

    private boolean success;
    private String message;
    private Object obj;
    private List<String> msgParams;

    public ResultBean() {
    }

    public ResultBean(boolean success, String message) {
        this.success = success;
        this.message = message;
    }

    public ResultBean(boolean success, String message, Object obj) {
        this.success = success;
        this.message = message;
        this.obj = obj;
    }

    public boolean isSuccess() {
        return success;
    }

    public void setSuccess(boolean success) {
        this.success = success;
    }

    public String getMessage() {
        return message;
    }

    public void setMessage(String message) {
        this.message = message;
    }

    public Object getObj() {
        return obj;
    }

    public void setObj(Object obj) {
        this.obj = obj;
    }

    public List<String> getMsgParams() {
        return msgParams;
    }

    public void setMsgParams(List<String> msgParams) {
        this.msgParams = msgParams;
    }
}
