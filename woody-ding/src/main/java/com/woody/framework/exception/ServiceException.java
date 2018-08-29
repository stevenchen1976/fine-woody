package com.woody.framework.exception;

public class ServiceException extends RuntimeException {

    private static final long serialVersionUID = 9171598378025729736L;

    public ServiceException(String message) {
        super(message);
    }

    public ServiceException(Throwable cause) {
        super(cause);
    }

    public ServiceException(String message, Throwable cause) {
        super(message, cause);
    }

    /** System specified exception code. */
    private String code;

    /** Parameters value for binding message. */
    private String[] params;

    public String getCode() {
        return code;
    }

    public void setCode(String code) {
        this.code = code;
    }

    public String[] getParams() {
        return params;
    }

    public void setParams(String[] params) {
        this.params = params;
    }
}
