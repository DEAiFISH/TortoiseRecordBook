package com.deaifish.tortoiserecordbook.bean;

import lombok.*;

import java.math.BigDecimal;
import java.util.Date;

/**
 * @description 乌龟
 *
 * @author DEAiFISH
 * @date 2023/11/22 13:06
 */
@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
@ToString
public class Tortoise {
    private String uid;
    private String id;  //UUID生成
    private String name;
    private Date firstTime; //入手时间，格式：yyyy-MM-dd
    private String headTilts; //头像图片路径
    private String memo;    //备注
    private BigDecimal price;   //入手价格
    private Status status;
    private Kind kind;
}
