package com.jun.soa.http;

import com.jun.soa.common.BeanMapper;
import com.jun.soa.page.PageResult;
import org.springframework.core.convert.converter.Converter;
import org.springframework.data.domain.Page;

import java.io.Serializable;
import java.util.Collection;

/**
 * 返回报文数据体
 * @author JUN
 *
 */
public class HttpBody implements Serializable {
    private static final long serialVersionUID = 1423782418741950900L;

    /**
     * 错误信息体
     */
    private HttpError error;
    /**
     * 报文数据体
     */
    private Object body;

    /**
     * 默认构造函数，错误体code为0-无错误
     */
    public HttpBody() {
        error=new HttpError();
    }

    /**
     * 返回数据实体
     * @param body
     */
    public HttpBody(Object body) {
        super();
        this.body = body;
    }


    /**
     * 将数据体转换为目标类型
     * @param body 数据体
     * @param cls 转换函数
     */
    public HttpBody(Object body,Class<?> cls) {
        convertBody(body,cls);
    }


    /**
     * 异常信息和异常编码
     * @param code
     * @param message
     */
    public HttpBody(String code,String message) {
        this.error=new HttpError(code,message);
    }

    public HttpError getError() {
        return error;
    }
    public void setError(HttpError error) {
        this.error = error;
    }
    public Object getBody() {
        return body;
    }
    public void setBody(Object body) {
        this.body = body;
    }

    private <T> void convertBody(Object body,final Class<T> cls){
        //为空不做处理
        if(body==null) return;
        //如果是spring Page分页类型，那么需要实现Converter
        if(body instanceof Page){
            PageResult<?> pageResult=(PageResult<?>)body;
            body=pageResult.map(new Converter<Object, T>() {

                @Override
                public T convert(Object source) {
                    return BeanMapper.map(source, cls);
                }

            });
        }else if(body instanceof Collection){ //集合类型
            this.body=BeanMapper.mapList((Collection<?>)body, cls);
        }else {
            this.body= BeanMapper.map(body, cls);
        }
    }
}
