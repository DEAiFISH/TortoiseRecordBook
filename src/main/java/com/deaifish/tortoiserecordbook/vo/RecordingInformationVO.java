package com.deaifish.tortoiserecordbook.vo;

import lombok.Builder;
import lombok.Data;

import java.util.Date;

/**
 * @description 饲养记录返回实体
 *
 * @author DEAiFISH
 * @date 2023/11/25 09:45
 */
@Data
@Builder
public class RecordingInformationVO {
    private String tid;
    private String iid;
    private String img; //储存路径地址，目标是个文件夹，最多9张
    private Date date;  //创建日期
    private String remark;
}
