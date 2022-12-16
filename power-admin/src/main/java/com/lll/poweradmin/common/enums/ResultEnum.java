package com.lll.poweradmin.common.enums;

/**
 * 错误信息枚举
 */
public enum ResultEnum {
    SUCCESS(20000,"ok"),
    ERROR(20001,"系统错误");

    /**
     * 状态码
     */
    private final int code;

    /**
     * 状态码信息
     */
    private final String message;

    ResultEnum(int code, String message) {
        this.code = code;
        this.message = message;
    }

    public int getCode() {
        return code;
    }

    public String getMessage() {
        return message;
    }
}
