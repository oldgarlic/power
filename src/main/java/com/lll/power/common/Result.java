package com.lll.power.common;

import com.lll.power.model.enums.ResultEnum;
import lombok.AllArgsConstructor;
import lombok.Data;

/**
 * 统一返回结果
 */
@Data
@AllArgsConstructor
public class Result<T> {
    private int code;
    private String message;
    private T data;

    public Result(int code, String message) {
        this.code = code;
        this.message = message;
    }

    public Result(ResultEnum resultEnum) {
        this(resultEnum.getCode(),resultEnum.getMessage());
    }

    public Result(ResultEnum resultEnum, T data) {
        this(resultEnum.getCode(),resultEnum.getMessage(),data);
    }

    public static Result<?> ok(){
        return new Result<>(ResultEnum.SUCCESS);
    }

    public static <T> Result<T> ok(T data){
        return new Result<>(ResultEnum.SUCCESS,data);
    }

    public static Result<?> error(){
        return error(ResultEnum.ERROR);
    }

    public static Result<?> error(ResultEnum resultEnum){
        return new Result<>(resultEnum);
    }

}
