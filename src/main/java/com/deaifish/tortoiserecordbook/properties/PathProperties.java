package com.deaifish.tortoiserecordbook.properties;

import lombok.Data;
import lombok.ToString;
import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.stereotype.Component;

/**
 * @description 路径配置文件类
 *
 * @author DEAiFISH
 * @date 2023/11/24 00:56
 */
@Data
@Component
@ConfigurationProperties(prefix = "path")
@ToString
public class PathProperties {
    public String userDefaultPhotoName;
    public String userHeadTiltsDirPath;
    public String tortoiseDefaultPhotoName;
    public String tortoiseHeadTiltsDirPath;
    public String tortoiseRecordingDirPath;
}
