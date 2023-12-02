package com.deaifish.tortoiserecordbook.bean;

import lombok.*;

/**
 * @description 用户
 *
 * @author DEAiFISH
 * @date 2023/11/22 13:16
 */
@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
@ToString
public class User {
    private String id;    //UUID生成
    private String account;
    private String passwd;
    private String headTilts;   //头像图片路径
}
