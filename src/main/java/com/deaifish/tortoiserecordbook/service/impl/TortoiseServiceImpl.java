package com.deaifish.tortoiserecordbook.service.impl;

import com.deaifish.tortoiserecordbook.bean.Tortoise;
import com.deaifish.tortoiserecordbook.exception.TortoiseException;
import com.deaifish.tortoiserecordbook.mapper.TortoiseMapper;
import com.deaifish.tortoiserecordbook.properties.PathProperties;
import com.deaifish.tortoiserecordbook.service.RecordingInformationService;
import com.deaifish.tortoiserecordbook.service.TortoiseService;
import com.deaifish.tortoiserecordbook.utils.ImgUtil;
import com.deaifish.tortoiserecordbook.utils.UUIDUtil;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

/**
 * @description 乌龟服务
 *
 * @author DEAiFISH
 * @date 2023/11/24 14:39
 */
@Service
@Slf4j
public class TortoiseServiceImpl implements TortoiseService {

    @Autowired
    private TortoiseMapper tortoiseMapper;
    @Autowired
    private RecordingInformationService recordingInformationService;
    @Autowired
    private PathProperties pathProperties;
    @Autowired
    private ImgUtil imgUtil;
    @Autowired
    private UUIDUtil uuidUtil;

    /**
     * @description 添加乌龟
     *
     * @author DEAiFISH
     * @date 2023/11/24 14:39
     * @param tortoise 乌龟信息
     */
    @Override
    public void addTortoise(Tortoise tortoise) {
        tortoise.setId(uuidUtil.getUUID());
        if (tortoise.getHeadTilts() == null || tortoise.getHeadTilts().isEmpty()) {
            tortoise.setHeadTilts(imgUtil.getImgURL(pathProperties.tortoiseHeadTiltsDirPath + pathProperties.tortoiseDefaultPhotoName));
        }
        tortoiseMapper.addTortoise(tortoise);

    }

    /**
     * @description 删除乌龟信息
     *
     * @author DEAiFISH
     * @date 2023/11/24 14:39
     * @param tid 乌龟ID
     */
    @Override
    public void delTortoise(String tid) {
        Tortoise tortoise = tortoiseMapper.selByTID(tid);
        if (tortoise == null) {
            throw new TortoiseException("乌龟不存在。");
        }
        //删除饲养记录
        recordingInformationService.delRecordingInformationByTID(tid);
        //删除仓库中的图片
        imgUtil.delImg(tortoise.getHeadTilts());
        tortoiseMapper.delTortoise(tid);
    }

    /**
     * @description 根据用户UID删除持有的乌龟
     *
     * @author DEAiFISH
     * @date 2023/11/29 20:29
     * @param uid 用户ID
     */
    public void delTortoiseByUID(String uid) {
        List<Tortoise> tortoises = tortoiseMapper.selByUID(uid);

        for (Tortoise t : tortoises) {
            delTortoise(t.getId());
        }
    }

    /**
     * @description 通过用户Id查询持有的乌龟
     *
     * @author DEAiFISH
     * @date 2023/11/24 14:39
     * @param uid 用户ID
     * @return java.util.List<com.deaifish.tortoiserecordbook.bean.Tortoise>
     */
    @Override
    public List<Tortoise> selByUID(String uid) {
        return tortoiseMapper.selByUID(uid);
    }

    /**
     * @description 根据乌龟ID查询乌龟信息
     *
     * @author DEAiFISH
     * @date 2023/11/24 17:07
     * @param tid 乌龟ID
     * @return com.deaifish.tortoiserecordbook.bean.Tortoise
     */
    @Override
    public Tortoise selByTID(String tid) {
        return tortoiseMapper.selByTID(tid);
    }

    /**
     * @description 查询所有乌龟
     *
     * @author DEAiFISH
     * @date 2023/11/24 14:39
     * @return java.util.List<com.deaifish.tortoiserecordbook.bean.Tortoise>
     */
    @Override
    public List<Tortoise> selAll() {
        return tortoiseMapper.selAll();
    }

    /**
     * @description 修改乌龟信息
     *
     * @author DEAiFISH
     * @date 2023/11/24 14:39
     * @param tortoise 乌龟信息
     */
    @Override
    public void updTortoise(Tortoise tortoise) {
        tortoiseMapper.updTortoise(tortoise);
    }
}
