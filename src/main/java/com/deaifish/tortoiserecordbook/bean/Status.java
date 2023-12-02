package com.deaifish.tortoiserecordbook.bean;

import com.baomidou.mybatisplus.annotation.EnumValue;
import com.fasterxml.jackson.annotation.JsonValue;
import lombok.Getter;

/**
 * @description 饲养状态
 *
 * @author DEAiFISH
 * @date 2023/11/22 13:09
 */
@Getter
public enum Status {
    FEEDING(0, "FEEDING", "喂养中"),
    SENT(1, "SENT", "送人了"),
    DIED(2, "DIED", "嗝屁了"),
    RELEASED(3, "RELEASED", "放生了");

    @EnumValue
    private final int code;
    @JsonValue
    private final String desc;
    private final String chDes;

    Status(int code, String desc, String chDes) {
        this.code = code;
        this.desc = desc;
        this.chDes = chDes;
    }


}
