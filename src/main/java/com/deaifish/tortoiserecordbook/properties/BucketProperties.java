package com.deaifish.tortoiserecordbook.properties;


import lombok.Data;
import lombok.ToString;
import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.stereotype.Component;

/**
 * @description Bucket配置文件类
 *
 * @author DEAiFISH
 * @date 2023/11/26 23:31
 */
@Data
@Component
@ConfigurationProperties(prefix = "bucket")
@ToString
public class BucketProperties {
    public String bucketPath;
    public String bucketWareHouse;
    public String accessKeyId;
    public String accessKeySecret;
    public String endpoint;
}
