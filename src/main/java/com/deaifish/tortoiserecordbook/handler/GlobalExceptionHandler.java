package com.deaifish.tortoiserecordbook.handler;

import com.deaifish.tortoiserecordbook.contanst.MSGConstant;
import com.deaifish.tortoiserecordbook.exception.RecordingInformationException;
import com.deaifish.tortoiserecordbook.exception.TortoiseException;
import com.deaifish.tortoiserecordbook.exception.UserException;
import com.deaifish.tortoiserecordbook.vo.ResultVO;
import jakarta.validation.ConstraintViolation;
import jakarta.validation.ConstraintViolationException;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpStatus;
import org.springframework.validation.BindException;
import org.springframework.validation.FieldError;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestControllerAdvice;

import java.io.IOException;
import java.sql.SQLIntegrityConstraintViolationException;
import java.util.List;
import java.util.Set;

/**
 * @description 统一错误处理器
 *
 * @author DEAiFISH
 * @date 2023/12/2 16:28
 */
@RestControllerAdvice
@Slf4j
public class GlobalExceptionHandler {

    /**
     * @description 捕获sql语句异常
     *              SQL 数据完整性约束违反的错误。当向数据库执行插入、更新或删除操作时，
     *              如果违反了表中定义的主键、唯一约束、外键约束或非空约束等数据完整性约束，就会抛出这个异常。
     *
     * @author DEAiFISH
     * @date 2023/12/2 23:14
     * @param ex 异常信息
     * @return Result
     */
    @ExceptionHandler(SQLIntegrityConstraintViolationException.class)
    public ResultVO<String> exceptionHandler(SQLIntegrityConstraintViolationException ex) {
        StringBuilder sb = new StringBuilder();
        String message = ex.getMessage();
        log.error("{}", ex.getMessage());
        if (message.startsWith("Duplicate entry")) {
            sb.append("用户已经存在，请重新输入。");
        } else if (message.endsWith("cannot be null")) {
            sb.append(ex.getMessage().split(" ")[1] + "不能为null，请重新输入。");
        } else {
            sb.append("用户信息出现未知异常。。。");
        }
        return new ResultVO<>(HttpStatus.BAD_GATEWAY.value(), MSGConstant.BAD_SQL_MSG, sb.toString());
    }


    /**
     * @description 处理 form data方式调用接口校验失败抛出的异常
     *
     * @author DEAiFISH
     * @date 2023/12/3 00:25
     * @param e 错误信息
     * @return com.deaifish.tortoiserecordbook.vo.ResultVO<java.lang.String>
     */
    @ExceptionHandler(BindException.class)
    public ResultVO<String> bindExceptionHandler(BindException e) {
        List<FieldError> fieldErrors = e.getBindingResult().getFieldErrors();
        StringBuilder sb = new StringBuilder();
        for (FieldError fieldError : fieldErrors) {
            sb.append(fieldError.getDefaultMessage());
        }
        return new ResultVO<>(HttpStatus.BAD_REQUEST.value(), MSGConstant.BAD_REQUEST_ARG_MSG, sb.toString());
    }

    /**
     * @description 处理 json 请求体调用接口校验失败抛出的异常
     *
     * @author DEAiFISH
     * @date 2023/12/3 00:25
     * @param e 错误信息
     * @return com.deaifish.tortoiserecordbook.vo.ResultVO<java.lang.String>
     */
    @ExceptionHandler(MethodArgumentNotValidException.class)
    public ResultVO<String> methodArgumentNotValidExceptionHandler(MethodArgumentNotValidException e) {
        List<FieldError> fieldErrors = e.getBindingResult().getFieldErrors();
        StringBuilder sb = new StringBuilder();
        for (FieldError fieldError : fieldErrors) {
            sb.append(fieldError.getDefaultMessage());
        }
        return new ResultVO<>(HttpStatus.BAD_REQUEST.value(), MSGConstant.BAD_REQUEST_ARG_MSG, sb.toString());
    }

    /**
     * @description 处理单个参数校验失败抛出的异常
     *
     * @author DEAiFISH
     * @date 2023/12/3 00:26
     * @param e 错误信息
     * @return com.deaifish.tortoiserecordbook.vo.ResultVO
     */
    @ExceptionHandler(ConstraintViolationException.class)
    public ResultVO<String> constraintViolationExceptionHandler(ConstraintViolationException e) {
        Set<ConstraintViolation<?>> constraintViolations = e.getConstraintViolations();
        StringBuilder sb = new StringBuilder();
        for (ConstraintViolation<?> violation : constraintViolations) {
            sb.append(violation.getMessage());
        }

        return new ResultVO<>(HttpStatus.BAD_REQUEST.value(), MSGConstant.BAD_REQUEST_ARG_MSG, sb.toString());
    }

    /**
     * @description 处理用户错误信息
     *
     * @author DEAiFISH
     * @date 2023/12/3 00:41
     * @param ex 错误信息
     * @return com.deaifish.tortoiserecordbook.vo.ResultVO<java.lang.String>
     */
    @ExceptionHandler(UserException.class)
    public ResultVO<String> userException(UserException ex) {
        return new ResultVO<>(HttpStatus.NOT_FOUND.value(), MSGConstant.BAD_BEAN_MSG, ex.getMessage());
    }

    /**
     * @description 处理乌龟错误信息
     *
     * @author DEAiFISH
     * @date 2023/12/3 00:41
     * @param ex 错误信息
     * @return com.deaifish.tortoiserecordbook.vo.ResultVO<java.lang.String>
     */
    @ExceptionHandler(TortoiseException.class)
    public ResultVO<String> tortoiseException(TortoiseException ex) {
        return new ResultVO<>(HttpStatus.NOT_FOUND.value(), MSGConstant.BAD_BEAN_MSG, ex.getMessage());
    }

    /**
     * @description 处理饲养记录错误信息
     *
     * @author DEAiFISH
     * @date 2023/12/3 00:41
     * @param ex 错误信息
     * @return com.deaifish.tortoiserecordbook.vo.ResultVO<java.lang.String>
     */
    @ExceptionHandler(RecordingInformationException.class)
    public ResultVO<String> recordingInformationException(RecordingInformationException ex) {
        return new ResultVO<>(HttpStatus.NOT_FOUND.value(), MSGConstant.BAD_BEAN_MSG, ex.getMessage());
    }

    /**
     * @description I/O读写错误
     *
     * @author DEAiFISH
     * @date 2023/12/3 01:19
     * @param ex 错误信息
     * @return com.deaifish.tortoiserecordbook.vo.ResultVO<java.lang.String>
     */
    @ExceptionHandler(IOException.class)
    public ResultVO<String> IOException(IOException ex) {
        return new ResultVO<>(HttpStatus.BAD_GATEWAY.value(), MSGConstant.BAD_IO_MSG, ex.getMessage());
    }

    /**
     * @description 指针越界错误
     *
     * @author DEAiFISH
     * @date 2023/12/3 01:27
     * @param ex 错误信息
     * @return com.deaifish.tortoiserecordbook.vo.ResultVO<java.lang.String>
     */
    @ExceptionHandler(IndexOutOfBoundsException.class)
    public ResultVO<String> indexOutOfBoundsException(IndexOutOfBoundsException ex) {
        return new ResultVO<>(HttpStatus.BAD_GATEWAY.value(), MSGConstant.BAD_PATH_MSG, ex.getMessage());
    }
}
