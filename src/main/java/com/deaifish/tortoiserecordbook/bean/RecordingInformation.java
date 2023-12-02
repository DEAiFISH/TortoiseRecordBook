package com.deaifish.tortoiserecordbook.bean;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.Date;

/**
 * @description 乌龟饲养记录
 *
 * @author DEAiFISH
 * @date 2023/11/22 22:45
 */
@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class RecordingInformation {
    private String tid;
    private String iid;
    private String img; //格式化多张图片路径 用`;`分割
    private Date date;  //入手时间,格式：yyyy-MM-dd
    private String remark;
    private Float size; //背甲长度（cm）
    private Float weight;   //体重（g）
}
