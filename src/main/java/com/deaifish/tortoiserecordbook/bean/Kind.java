package com.deaifish.tortoiserecordbook.bean;

import com.baomidou.mybatisplus.annotation.EnumValue;
import com.fasterxml.jackson.annotation.JsonValue;
import lombok.Getter;

/**
 * @description 乌龟种类
 *
 * @author DEAiFISH
 * @date 2023/11/22 13:15
 */
@Getter
public enum Kind {
    CYCLEMYSTRIFASCIATA(0, "CYCLEMYSTRIFASCIATA", "三线闭壳龟"),
    CHINEMYSREEVESIIS(1, "CHINEMYSREEVESIIS", "中华草龟"),
    MAUREMYSMUTICA(2, "MAUREMYSMUTICA", "黄喉拟水龟"),
    OCADIASINENSIS(3, "OCADIASINENSIS", "中华花龟"),
    PLATYSTERNONMEGACEPHALUM(4, "PLATYSTERNONMEGACEPHALUM", "鹰嘴龟"),
    TRACHEMYSAELEGANS(5, "TRACHEMYSAELEGANS", "巴西红耳龟"),
    CHELODINASIEBENROCKI(6, "CHELODINASIEBENROCKI", "蛇颈龟"),
    CARETTOCHELYSINSCULPTA(7, "CARETTOCHELYSINSCULPTA", "猪鼻龟");

    @EnumValue
    private final int code;
    @JsonValue
    private final String desc;
    private final String chDes;

    Kind(int code, String desc, String chDes) {
        this.code = code;
        this.desc = desc;
        this.chDes = chDes;
    }
}
