package com.deaifish.tortoiserecordbook.exception;

/**
 * @description 自定义用户异常类
 *
 * @author DEAiFISH
 * @date 2023/12/2 16:44
 */
public class UserException extends ServiceException {
    public UserException(String message) {
        super(message);
    }
}
