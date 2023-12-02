package com.deaifish.tortoiserecordbook.vo;

import lombok.Builder;
import lombok.Data;

/**
 * @description 体重信息
 *
 * @author DEAiFISH
 * @date 2023/11/25 08:43
 */
@Data
@Builder
public class WeightVO {
    private float weight;
    private float size;
}
