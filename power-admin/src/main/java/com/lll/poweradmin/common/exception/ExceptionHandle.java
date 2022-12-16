package com.lll.poweradmin.common.exception;

import com.lll.poweradmin.common.Result;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestControllerAdvice;

@RestControllerAdvice
public class ExceptionHandle {

    @ExceptionHandler(value = {UserException.class})
    public Result<?> catchUserException(UserException e){
        return Result.error(e.getMessage());
    }

    @ExceptionHandler(value = {Exception.class})
    public Result<?> catchOtherException(Exception e){
        return Result.error(e.getMessage());
    }
}
