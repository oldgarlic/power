package com.lll.poweradmin.common.exception;

import com.lll.poweradmin.common.Result;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestControllerAdvice;

@RestControllerAdvice()
public class GlobalExceptionController {

    @ExceptionHandler(value = {Exception.class})
    public Result<?> catchOtherException(Exception e){
        return Result.error(e.getMessage());
    }
}
