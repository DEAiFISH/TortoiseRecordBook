package com.deaifish.tortoiserecordbook.dto;

import jakarta.validation.constraints.NotEmpty;
import jakarta.validation.constraints.PositiveOrZero;
import lombok.Builder;
import lombok.Data;

import java.util.Date;

/**
 * @description 乌龟饲养记录
 *
 * @author DEAiFISH
 * @date 2023/11/27 23:08
 */
@Data
@Builder
public class RecordingInformationUpdDTO {
    @NotEmpty(message = "饲养记录ID不能为空。")
    private String iid;
    private Date date;  //创建日期
    private String remark;
    @PositiveOrZero(message = "背甲长度必须大于0。")
    private Float size; //背甲长度（cm）
    @PositiveOrZero(message = "体重必须大于0。")
    private Float weight;   //体重（g）
}
