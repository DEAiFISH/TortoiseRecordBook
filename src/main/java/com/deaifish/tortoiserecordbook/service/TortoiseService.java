package com.deaifish.tortoiserecordbook.service;

import com.deaifish.tortoiserecordbook.bean.Tortoise;

import java.util.List;

/**
 * @description 乌龟服务
 *
 * @author DEAiFISH
 * @date 2023/11/24 14:34
 */
public interface TortoiseService {
    void addTortoise(Tortoise tortoise);

    void delTortoise(String tid);

    void delTortoiseByUID(String uid);

    List<Tortoise> selByUID(String uid);

    Tortoise selByTID(String tid);

    List<Tortoise> selAll();

    void updTortoise(Tortoise tortoise);
}
