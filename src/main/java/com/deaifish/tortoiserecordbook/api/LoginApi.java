package com.deaifish.tortoiserecordbook.api;

import java.lang.annotation.*;

/**
 * @description 登录验证
 *
 * @author DEAiFISH
 * @date 2023/12/3 01:40
 */
@Documented
@Retention(RetentionPolicy.RUNTIME)
@Target({ElementType.TYPE, ElementType.METHOD})

public @interface LoginApi {
    /**
     * 是否需要登录
     */
    boolean value() default true;

}
