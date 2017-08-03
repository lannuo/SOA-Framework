package com.jun.soa.handler;

import com.jun.soa.common.ErrorMsg;
import com.jun.soa.http.HttpResult;
import org.springframework.http.HttpStatus;
import org.springframework.validation.FieldError;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.annotation.*;

import java.util.List;

/**
 * 全局异常控制
 */
@ControllerAdvice
public class GlobalExceptionHandler {

    /**
     * 前台传入参数vo中有字段异常
     * @param e
     * @return
     */
    @ExceptionHandler(MethodArgumentNotValidException.class)
    @ResponseStatus(HttpStatus.BAD_REQUEST)
    @ResponseBody
    public HttpResult handleMethodArgumentNotValidException(MethodArgumentNotValidException e){
        List<FieldError> fieldErrors=e.getBindingResult().getFieldErrors();
        for(FieldError fieldError: fieldErrors){
            ErrorMsg.add(fieldError.getDefaultMessage());
        }
        return new HttpResult("400",ErrorMsg.getMsg());
    }

    /**
     * 运行异常
     * @param e
     * @return
     */
    @ExceptionHandler(RuntimeException.class)
    @ResponseStatus(HttpStatus.BAD_REQUEST)
    @ResponseBody
    public HttpResult handleRuntimeException(RuntimeException e){
        return new HttpResult("500",e.getMessage());
    }
}
