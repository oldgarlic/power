package com.lll.poweradmin.common;

import com.lll.poweradmin.model.enums.ResultEnum;
import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.AllArgsConstructor;
import lombok.Data;

/**
 * 统一返回结果
 */
@Data
@AllArgsConstructor
@ApiModel(value="Result对象", description="统一结果返回对象")
public class Result<T> {
    @ApiModelProperty(value = "状态码")
    private int code;
    @ApiModelProperty(value = "状态消息")
    private String message;
    @ApiModelProperty(value = "返回数据")
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
