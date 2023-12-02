package com.deaifish.tortoiserecordbook.utils;

import com.aliyun.oss.ClientException;
import com.aliyun.oss.OSS;
import com.aliyun.oss.OSSClientBuilder;
import com.aliyun.oss.OSSException;
import com.aliyun.oss.common.auth.CredentialsProvider;
import com.aliyun.oss.common.auth.DefaultCredentialProvider;
import com.aliyun.oss.model.PutObjectRequest;
import com.deaifish.tortoiserecordbook.properties.BucketProperties;
import com.deaifish.tortoiserecordbook.properties.PathProperties;
import lombok.extern.slf4j.Slf4j;
import net.coobird.thumbnailator.Thumbnails;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import java.io.ByteArrayInputStream;
import java.io.ByteArrayOutputStream;
import java.io.IOException;
import java.io.InputStream;

/**
 * @description 工具类
 *
 * @author DEAiFISH
 * @date 2023/11/23 17:21
 */
@Slf4j
@Component
public class ImgUtil {
    @Autowired
    BucketProperties bucketProperties;
    @Autowired
    PathProperties pathProperties;

    /**
     * @description 获取OSS实例
     *
     * @author DEAiFISH
     * @date 2023/11/28 00:39
     * @return com.aliyun.oss.OSS
     */
    private OSS getOSS() {
        String endpoint = bucketProperties.endpoint;
        String id = bucketProperties.accessKeyId;
        String secret = bucketProperties.accessKeySecret;
        CredentialsProvider credentialsProvider = new DefaultCredentialProvider(id, secret);

        // 创建OSSClient实例。
        return new OSSClientBuilder().build(endpoint, credentialsProvider);
    }

    /**
     * @description 单文件上传
     *
     * @author DEAiFISH
     * @date 2023/11/27 10:22
     * @param inputStream 文件输入流
     * @param fileName 文件名
     * @param path 储存路径
     * @return String 上传文件的完整路径
     */
    public String uploadImg(InputStream inputStream, String fileName, String path) {
        OSS ossClient = getOSS();
        try {
            InputStream is = thumbnail(inputStream);
            // 创建PutObjectRequest对象。
            PutObjectRequest putObjectRequest = new PutObjectRequest(bucketProperties.bucketWareHouse, path + fileName, is);
            // 创建PutObject请求。
            ossClient.putObject(putObjectRequest);

        } catch (OSSException oe) {
            System.out.println("Caught an OSSException, which means your request made it to OSS, "
                    + "but was rejected with an error response for some reason.");
            System.out.println("Error Message:" + oe.getErrorMessage());
            System.out.println("Error Code:" + oe.getErrorCode());
            System.out.println("Request ID:" + oe.getRequestId());
            System.out.println("Host ID:" + oe.getHostId());
        } catch (ClientException ce) {
            System.out.println("Caught an ClientException, which means the client encountered "
                    + "a serious internal problem while trying to communicate with OSS, "
                    + "such as not being able to access the network.");
            System.out.println("Error Message:" + ce.getMessage());
        } finally {
            if (ossClient != null) {
                ossClient.shutdown();
            }
        }
        return getImgURL(path + fileName);
    }

    /**
     * @description 删除图片
     *
     * @author DEAiFISH
     * @date 2023/11/27 15:23
     * @param path 图片相对路径
     */
    public void delImg(String path) {
        if (path == null || path.isEmpty()) {
            return;
        }
        try {
            path = path.substring(bucketProperties.bucketPath.length() + 1);
        } catch (Exception e) {
            throw new StringIndexOutOfBoundsException(e.getMessage());
        }
        if ((pathProperties.userHeadTiltsDirPath + pathProperties.userDefaultPhotoName).equals(path)
                || (pathProperties.tortoiseHeadTiltsDirPath + pathProperties.tortoiseDefaultPhotoName).equals(path)) {
            //如果路径为默认图片路径，则退出
            return;
        }

        OSS ossClient = getOSS();

        try {
            // 删除文件或目录。如果要删除目录，目录必须为空。
            ossClient.deleteObject(bucketProperties.bucketWareHouse, path);
        } catch (OSSException oe) {
            System.out.println("Caught an OSSException, which means your request made it to OSS, "
                    + "but was rejected with an error response for some reason.");
            System.out.println("Error Message:" + oe.getErrorMessage());
            System.out.println("Error Code:" + oe.getErrorCode());
            System.out.println("Request ID:" + oe.getRequestId());
            System.out.println("Host ID:" + oe.getHostId());
        } catch (ClientException ce) {
            System.out.println("Caught an ClientException, which means the client encountered "
                    + "a serious internal problem while trying to communicate with OSS, "
                    + "such as not being able to access the network.");
            System.out.println("Error Message:" + ce.getMessage());
        } finally {
            if (ossClient != null) {
                ossClient.shutdown();
            }
        }
    }

    /**
     * @description 获取文件路径
     *
     * @author DEAiFISH
     * @date 2023/11/27 14:44
     * @param path 图片路径
     * @return java.lang.String 访问路径
     */
    public String getImgURL(String path) {
        return bucketProperties.bucketPath + "/" + path;
    }

    /**
     * @description 压缩图片
     *
     * @author DEAiFISH
     * @date 2023/11/28 11:08
     * @param fis 图片文件
     * @return java.io.InputStream
     */
    public InputStream thumbnail(InputStream fis) {
        ByteArrayOutputStream baos = new ByteArrayOutputStream();
        InputStream is = null;
        byte[] buffer;

        try {
            // 先尝试压缩并保存图片
            Thumbnails.of(fis)
                    .scale(0.5f)
                    .outputQuality(0.15f)
                    .toOutputStream(baos);

            buffer = baos.toByteArray();
            is = new ByteArrayInputStream(buffer);
        } catch (IOException e) {

            log.info("thumbnail ============> {}", e.getMessage());
        }
        return is;
    }


}
