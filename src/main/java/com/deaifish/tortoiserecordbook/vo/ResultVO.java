package com.deaifish.tortoiserecordbook.vo;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.List;

/**
 * @description 异常返回值
 *
 * @author DEAiFISH
 * @date 2023/12/2 23:41
 */
@Data
@NoArgsConstructor
@AllArgsConstructor
public class ResultVO<T> {
    private int code;
    private String msg;
    private List<T> data;
}
