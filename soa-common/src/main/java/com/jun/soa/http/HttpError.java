package com.jun.soa.http;

import java.io.Serializable;

/**
 *返回报文错误信息
 */
public class HttpError implements Serializable{
    private static final long serialVersionUID = -4786551569279877111L;

    /**
     * 错误码  默认0-没错
     */
    private String code="0";

    /**
     * 错误信息
     */
    private String message;

    public HttpError() {
        super();
    }

    public HttpError(String code) {
        super();
        this.code = code;
    }

    public HttpError(String code, String message) {
        super();
        this.code = code;
        this.message = message;
    }

    public String getCode() {
        return code;
    }

    public void setCode(String code) {
        this.code = code;
    }

    public String getMessage() {
        return message;
    }

    public void setMessage(String message) {
        this.message = message;
    }

}
