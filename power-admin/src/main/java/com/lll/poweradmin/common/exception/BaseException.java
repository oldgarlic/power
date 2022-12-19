package com.lll.poweradmin.common.exception;


import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.NoArgsConstructor;

public abstract class BaseException extends RuntimeException{
    private int code;
    private String message;
    public BaseException(String message) {
        super(message);
    }
}
