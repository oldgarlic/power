package com.lll.poweradmin.common.exception;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.NoArgsConstructor;

/**
 * 业务异常
 *
 * @author oldgarlic
 * @since 2022/12/11
 */
public class ServiceException extends BaseException{

    public ServiceException(String message) {
        super(message);
    }
}
