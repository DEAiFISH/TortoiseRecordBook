package com.deaifish.tortoiserecordbook.service;

import java.io.InputStream;

/**
 * @description img服务
 *
 * @author DEAiFISH
 * @date 2023/11/27 09:19
 */
public interface IMGService {
    String uploadImg(InputStream fis, String path);

    void delImg(String path);

    String updImg(String oldPath, InputStream fis, String path);
}
