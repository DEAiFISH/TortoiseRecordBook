package com.deaifish.tortoiserecordbook.utils;

import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Component;

import java.util.UUID;

/**
 * @description UUID工具类
 *
 * @author DEAiFISH
 * @date 2023/11/29 19:11
 */
@Slf4j
@Component
public class UUIDUtil {
    /**
     * @description 随机生成UUID
     *
     * @author DEAiFISH
     * @date 2023/11/23 17:22
     * @return java.lang.String
     */
    public String getUUID() {
        return UUID.randomUUID().toString().replace("-", "").toLowerCase();
    }

}
