package com.deaifish.tortoiserecordbook.mapper;

import com.deaifish.tortoiserecordbook.bean.RecordingInformation;
import org.apache.ibatis.annotations.*;

import java.util.List;

/**
 * @description 饲养记录mapper接口
 *
 * @author DEAiFISH
 * @date 2023/11/24 14:49
 */
public interface RecordingInformationMapper {
    /**
     * @description 查询所有饲养信息
     *
     * @author DEAiFISH
     * @date 2023/11/25 09:44
     * @return java.util.List<com.deaifish.tortoiserecordbook.bean.TortoiseInformation>
     */
    @Results(id = "recordingInformationMap", value = {
            @Result(column = "T_ID", property = "tid"),
            @Result(column = "I_ID", property = "iid"),
            @Result(column = "I_IMG", property = "img"),
            @Result(column = "I_DATE", property = "date"),
            @Result(column = "I_REMARK", property = "remark"),
            @Result(column = "I_SIZE", property = "size"),
            @Result(column = "I_WEIGHT", property = "weight")
    })
    @Select("select * from RECORDING_INFORMATION")
    List<RecordingInformation> selAll();

    /**
     * @description 根据乌龟ID查询对应的饲养信息
     *
     * @author DEAiFISH
     * @date 2023/11/24 14:57
     * @param tid 乌龟ID
     * @return java.util.List<com.deaifish.tortoiserecordbook.bean.TortoiseInformation>
     */
    @ResultMap("recordingInformationMap")
    @Select("select * from RECORDING_INFORMATION " +
            "where T_ID = #{tid} " +
            "order by I_ID,I_DATE")
    List<RecordingInformation> selByTID(String tid);

    /**
     * @description 根据饲养记录ID查询饲养记录信息
     *
     * @author DEAiFISH
     * @date 2023/11/25 08:04
     * @param iid 饲养记录ID
     * @return com.deaifish.tortoiserecordbook.bean.TortoiseInformation
     */
    @ResultMap("recordingInformationMap")
    @Select("select * from RECORDING_INFORMATION " +
            "where I_ID = #{iid} ")
    RecordingInformation selByIId(String iid);

    /**
     * @description 添加饲养记录
     *
     * @author DEAiFISH
     * @date 2023/11/24 14:59
     * @param recordingInformation 饲养记录信息
     */
    @ResultMap("recordingInformationMap")
    @Insert("insert into RECORDING_INFORMATION(T_ID, I_ID, I_IMG, I_DATE, I_REMARK, I_SIZE, I_WEIGHT) values " +
            "(#{tid},#{iid},#{img},#{date},#{remark},#{size},#{weight})")
    void addRecordingInformation(RecordingInformation recordingInformation);

    /**
     * @description 修改饲养记录信息
     *
     * @author DEAiFISH
     * @date 2023/11/24 15:05
     * @param recordingInformation 饲养记录信息
     */
    @ResultMap("recordingInformationMap")
    @Update("update RECORDING_INFORMATION set " +
            "I_IMG = #{img}," +
            "I_DATE = #{date}," +
            "I_REMARK = #{remark}, " +
            "I_SIZE = #{size}, " +
            "I_WEIGHT = #{weight} " +
            "where I_ID = #{iid}")
    void updRecordingInformation(RecordingInformation recordingInformation);

    /**
     * @description 通过饲养记录ID删除饲养记录
     *
     * @author DEAiFISH
     * @date 2023/11/24 15:06
     * @param iId 饲养记录ID
     */
    @Delete("delete from RECORDING_INFORMATION where I_ID = #{iid}")
    void delRecordingInformation(String iId);

    /**
     * @description 根据乌龟ID删除对应乌龟的所有饲养记录
     *
     * @author DEAiFISH
     * @date 2023/11/25 08:21
     * @param tid 乌龟ID
     */
    @Delete("delete from RECORDING_INFORMATION where T_ID = #{tid}")
    void delRecordingInformationByTID(String tid);
}
