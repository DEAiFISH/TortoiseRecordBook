package com.deaifish.tortoiserecordbook.dto;

import com.deaifish.tortoiserecordbook.bean.Kind;
import com.deaifish.tortoiserecordbook.bean.Status;
import jakarta.validation.constraints.NotEmpty;
import jakarta.validation.constraints.PositiveOrZero;
import lombok.Builder;
import lombok.Data;

import java.math.BigDecimal;
import java.util.Date;

/**
 * @description 乌龟添加DTO
 *
 * @author DEAiFISH
 * @date 2023/11/27 21:31
 */
@Data
@Builder
public class TortoiseAddDTO {
    @NotEmpty(message = "用户ID不能为空。")
    private String uid;
    private String name;
    private Date firstTime; /*入手时间*/
    private String headTilts; /*头像图片路径*/
    private String memo;    /*备注*/
    @PositiveOrZero(message = "价格必须大于0。")
    private BigDecimal price;   /*入手价格*/
    private Status status;
    private Kind kind;
}
