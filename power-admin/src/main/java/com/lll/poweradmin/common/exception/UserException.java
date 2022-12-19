package com.lll.poweradmin.common.exception;


import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.NoArgsConstructor;


/**
 * 用户异常
 *
 * @author oldgarlic
 * @since 2022/12/12
 */
public class UserException extends BaseException{
    public UserException(String message) {
        super(message);
    }
}
