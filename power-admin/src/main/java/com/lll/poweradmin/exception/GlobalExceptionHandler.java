package com.lll.poweradmin.exception;

import com.lll.poweradmin.common.Result;
import org.springframework.validation.BindingResult;
import org.springframework.validation.FieldError;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestControllerAdvice;

@RestControllerAdvice
public class GlobalExceptionHandler {

    @ExceptionHandler({MethodArgumentNotValidException.class})
    public Result<?> handleMethodArgumentNotValidException(MethodArgumentNotValidException exception){
        BindingResult bindingResult = exception.getBindingResult();
        FieldError fieldError = bindingResult.getFieldError();
        assert fieldError != null;
        String defaultMessage = fieldError.getDefaultMessage();
        return Result.ok(defaultMessage);
    }
}
