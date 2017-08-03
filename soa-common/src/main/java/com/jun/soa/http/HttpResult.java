package com.jun.soa.http;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.util.MultiValueMap;

import java.io.Serializable;


/**
 * 返回报文封住类
 * @author JUN
 *
 */
public class HttpResult extends ResponseEntity<HttpBody> implements Serializable {
    private static final long serialVersionUID = -1661141391011004965L;

    /**
     * FS_STATUSCODE_OK:TODO(返回成功状态码).
     */
    private static final HttpStatus FS_STATUSCODE_OK= HttpStatus.OK;

    /**
     * Creates a new instance of HttpResult.
     * Description: 报文构造函数,不返回数据体,那么返回error状态为0-成功.
     */
    public HttpResult(){
        super(new HttpBody(),FS_STATUSCODE_OK);
    }

    /**
     * Creates a new instance of HttpResult.
     *
     * @param body
     */
    public HttpResult(Object body) {
        super(new HttpBody(body), FS_STATUSCODE_OK);
    }

    /**
     * Creates a new instance of HttpResult.
     *
     * @param body 报文数据
     * @param cls  转换目标类型(返回报文数据对象)
     */
    public HttpResult(Object body, Class<?> cls) {
        super(new HttpBody(body, cls), FS_STATUSCODE_OK);
    }

    /**
     * Creates a new instance of HttpResult.
     *  报文构造函数(异常码为0)
     * @param message 异常信息
     * @param httpStatus  浏览器状态(为空时默认返回200)
     */
    public HttpResult(String message, HttpStatus httpStatus) {
        super(new HttpBody("0", message), httpStatus == null ? FS_STATUSCODE_OK : httpStatus);
    }

    /**
     * Creates a new instance of HttpResult.
     *
     * @param code 异常编码
     * @param message 异常信息
     */
    public HttpResult(String code, String message) {
        super(new HttpBody(code, message), FS_STATUSCODE_OK);
    }

    /**
     * Creates a new instance of HttpResult.
     *
     * @param code 异常编码
     * @param message 异常信息
     * @param httpStatus 浏览器状态
     */
    public HttpResult(String code, String message, HttpStatus httpStatus) {
        super(new HttpBody(code, message), httpStatus);
    }

    /**
     * Creates a new instance of HttpResult.
     *
     * @param httpBody报文数据体对象
     * @param headers  返回报文头
     */
    public HttpResult(HttpBody httpBody, MultiValueMap<String, String> headers) {
        super(httpBody, headers, FS_STATUSCODE_OK);
    }
}
