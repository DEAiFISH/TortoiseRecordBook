package com.deaifish.tortoiserecordbook.service.impl;

import com.deaifish.tortoiserecordbook.service.IMGService;
import com.deaifish.tortoiserecordbook.utils.ImgUtil;
import com.deaifish.tortoiserecordbook.utils.UUIDUtil;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.io.InputStream;

/**
 * @description img服务实现类
 *
 * @author DEAiFISH
 * @date 2023/11/27 09:19
 */
@Service
@Slf4j
public class IMGServiceImpl implements IMGService {
    @Autowired
    private ImgUtil imgUtil;
    @Autowired
    private UUIDUtil uuidUtil;

    /**
     * @description 上传用户头像
     *
     * @author DEAiFISH
     * @date 2023/11/27 10:03
     * @param fis 头像文件
     * @param path 储存路径
     * @return String 头像文件名称
     */
    @Override
    public String uploadImg(InputStream fis, String path) {
        String fileName = uuidUtil.getUUID() + ".png";
        fileName = imgUtil.uploadImg(fis, fileName, path);
        return fileName;
    }

    /**
     * @param path 图片相对路径
     * @description 删除头像
     * @author DEAiFISH
     * @date 2023/11/27 15:24
     */
    @Override
    public void delImg(String path) {
        imgUtil.delImg(path);
    }

    /**
     * @description 修改头像
     *
     * @param oldPath 旧图像相对路径
     * @param fis     新头像文件流
     * @param path 储存路径
     * @return boolean
     * @description 修改头像
     * @author DEAiFISH
     * @date 2023/11/27 15:26
     */
    @Override
    public String updImg(String oldPath, InputStream fis, String path) {
        imgUtil.delImg(oldPath);
        String fileName = uuidUtil.getUUID() + ".png";
        return imgUtil.uploadImg(fis, fileName, path);
    }
}
