package com.jun.soa.common;


import com.jun.soa.utils.BaseUtil;

import java.text.MessageFormat;

public class ErrorMsg {

    private final static String FS_NL = "\n";

    private static ThreadLocal<StringBuilder> S_ERROR = new ThreadLocal<StringBuilder>();

    /**
     * 添加错误信息
     */
    public static void add(String msg) {
        getStringBuilder().append(msg + FS_NL);
    }

    /**
     * 验证是否存在错误信息，如有错误信息就抛出异常
     */
    public static void vaildErrorMsg() {
        String message = getMsg();
        throwException(message);
    }

    /**
     * 抛出异常
     */
    public static void throwException(String msg, Object... objects) {
        if (BaseUtil.isNotEmpty(msg)) {
            if (objects.length > 0) {
                msg = MessageFormat.format(msg, objects);
            }
            throw new RuntimeException(msg);
        }
    }

    /**
     *
     * 抛出异常
     */
    public static void throwException(String msg, Exception ex) {
        if (BaseUtil.isNotEmpty(msg)) {
            throw new RuntimeException(msg, ex);
        }
    }

    /**
     * 取回msg
     */
    public static String getMsg() {
        String msg = getStringBuilder().toString();
        S_ERROR.set(null);
        return msg;
    }

    private static StringBuilder getStringBuilder() {
        if (S_ERROR.get() == null) {
            S_ERROR.set(new StringBuilder());
        }
        return S_ERROR.get();
    }
}
