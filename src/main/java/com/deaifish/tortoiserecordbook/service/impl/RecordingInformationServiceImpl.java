package com.deaifish.tortoiserecordbook.service.impl;

import com.deaifish.tortoiserecordbook.bean.RecordingInformation;
import com.deaifish.tortoiserecordbook.mapper.RecordingInformationMapper;
import com.deaifish.tortoiserecordbook.service.RecordingInformationService;
import com.deaifish.tortoiserecordbook.utils.ImgUtil;
import com.deaifish.tortoiserecordbook.utils.UUIDUtil;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

/**
 * @description 饲养记录服务实现类
 *
 * @author DEAiFISH
 * @date 2023/11/24 16:23
 */
@Service
@Slf4j
public class RecordingInformationServiceImpl implements RecordingInformationService {
    @Autowired
    private RecordingInformationMapper recordingInformationMapper;
    @Autowired
    private ImgUtil imgUtil;
    @Autowired
    private UUIDUtil uuidUtil;

    /**
     * @description 查询所有乌龟信息
     *
     * @author DEAiFISH
     * @date 2023/11/24 16:24
     * @return java.util.List<com.deaifish.tortoiserecordbook.bean.TortoiseInformation>
     */
    @Override
    public List<RecordingInformation> selAllRecordingInformation() {
        return recordingInformationMapper.selAll();
    }

    /**
     * @description 通过乌龟ID查询所有饲养记录
     *
     * @author DEAiFISH
     * @date 2023/11/24 16:26
     * @param tid 乌龟ID
     * @return java.util.List<com.deaifish.tortoiserecordbook.bean.TortoiseInformation>
     */
    @Override
    public List<RecordingInformation> selRecordingInformationByTID(String tid) {
        return recordingInformationMapper.selByTID(tid);
    }

    /**
     * @param iid 饲养记录ID
     * @return com.deaifish.tortoiserecordbook.vo.TortoiseInformationVO
     * @description 根据饲养记录ID查询饲养记录
     * @author DEAiFISH
     * @date 2023/11/25 09:50
     */
    @Override
    public RecordingInformation selRecordingInformationByIID(String iid) {
        return recordingInformationMapper.selByIId(iid);
    }

    /**
     * @description 添加饲养记录
     *
     * @author DEAiFISH
     * @date 2023/11/24 16:28
     * @param recordingInformation 饲养记录
     */
    @Override
    public void addRecordingInformation(RecordingInformation recordingInformation) {
        recordingInformation.setIid(uuidUtil.getUUID());
        recordingInformationMapper.addRecordingInformation(recordingInformation);
    }

    /**
     * @description 更新饲养记录
     *
     * @author DEAiFISH
     * @date 2023/11/24 16:28
     * @param recordingInformation 饲养记录
     */
    @Override
    public void updRecordingInformation(RecordingInformation recordingInformation) {
        recordingInformationMapper.updRecordingInformation(recordingInformation);
    }

    /**
     * @description 删除饲养记录
     *
     * @author DEAiFISH
     * @date 2023/11/24 16:42
     * @param iid 饲养记录ID
     */
    @Override
    public void delRecordingInformationByIID(String iid) {
        recordingInformationMapper.delRecordingInformation(iid);
    }

    /**
     * @description 根据乌龟ID删除饲养记录
     *
     * @author DEAiFISH
     * @date 2023/11/29 20:10
     * @param tid 乌龟ID
     */
    @Override
    public void delRecordingInformationByTID(String tid) {
        List<RecordingInformation> recordingInformationList = recordingInformationMapper.selByTID(tid);

        //删除仓库中的图片
        for (RecordingInformation recordingInformation : recordingInformationList) {
            String path = recordingInformation.getImg();
            if (path == null || path.isEmpty()) {
                //未添加图片
                continue;
            }
            for (String img : path.split(";")) {
                imgUtil.delImg(img);
            }
        }
        recordingInformationMapper.delRecordingInformationByTID(tid);
    }

}
