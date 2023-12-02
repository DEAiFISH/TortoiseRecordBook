package com.deaifish.tortoiserecordbook.dto;

import jakarta.validation.constraints.NotEmpty;
import jakarta.validation.constraints.Size;
import lombok.Builder;
import lombok.Data;

/**
 * @description 用户注册DTO
 *
 * @author DEAiFISH
 * @date 2023/11/27 20:36
 */
@Data
@Builder
public class UserSingUpDTO {
    @NotEmpty(message = "账号不能为空。")
    @Size(min = 10, max = 20, message = "账号长度应为10-20。")
    private String account;
    @NotEmpty(message = "密码不能为空。")
    @Size(min = 10, max = 20, message = "密码长度应为10-20。")
    private String passwd;
    private String headTilts;
}
