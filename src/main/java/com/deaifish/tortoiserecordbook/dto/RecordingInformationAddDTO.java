package com.deaifish.tortoiserecordbook.dto;

import jakarta.validation.constraints.NotEmpty;
import jakarta.validation.constraints.PositiveOrZero;
import lombok.Builder;
import lombok.Data;

import java.util.Date;

/**
 * @description 添加饲养记录
 *
 * @author DEAiFISH
 * @date 2023/11/29 19:07
 */
@Data
@Builder
public class RecordingInformationAddDTO {
    @NotEmpty(message = "乌龟ID不能为空")
    private String tid;
    private Date date;  //创建日期
    private String remark;
    @PositiveOrZero(message = "背甲长度必须大于0。")
    private Float size; //背甲长度（cm）
    @PositiveOrZero(message = "体重必须大于0。")
    private Float weight;   //体重（g）
    private String img;
}
