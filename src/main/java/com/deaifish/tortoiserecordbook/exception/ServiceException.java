package com.deaifish.tortoiserecordbook.exception;

/**
 * @description 自定义服务异常类
 *
 * @author DEAiFISH
 * @date 2023/12/2 23:08
 */
public class ServiceException extends RuntimeException {
    public ServiceException(String message) {
        super(message);
    }
}
