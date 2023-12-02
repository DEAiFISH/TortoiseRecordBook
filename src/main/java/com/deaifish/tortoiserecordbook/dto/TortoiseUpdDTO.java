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
 * @description 修改乌龟DTO
 *
 * @author DEAiFISH
 * @date 2023/11/29 21:02
 */
@Data
@Builder
public class TortoiseUpdDTO {
    @NotEmpty(message = "乌龟ID必须大于0。")
    private String id;
    private String name;
    private Date firstTime; /*入手时间*/
    private String memo;    /*备注*/
    @PositiveOrZero(message = "价格必须大于0。")
    private BigDecimal price;   /*入手价格*/
    private Status status;
    private Kind kind;
}
