package com.deaifish.tortoiserecordbook.service;

import com.deaifish.tortoiserecordbook.bean.RecordingInformation;

import java.util.List;

/**
 * @description 饲养记录服务
 *
 * @author DEAiFISH
 * @date 2023/11/24 16:20
 */
public interface RecordingInformationService {
    List<RecordingInformation> selAllRecordingInformation();

    List<RecordingInformation> selRecordingInformationByTID(String tid);

    RecordingInformation selRecordingInformationByIID(String iid);

    void addRecordingInformation(RecordingInformation recordingInformation);

    void updRecordingInformation(RecordingInformation recordingInformation);

    void delRecordingInformationByIID(String iid);

    void delRecordingInformationByTID(String tid);
}
