package com.deaifish.tortoiserecordbook.mapper;

import com.baomidou.mybatisplus.core.handlers.MybatisEnumTypeHandler;
import com.deaifish.tortoiserecordbook.bean.Kind;
import com.deaifish.tortoiserecordbook.bean.Status;
import com.deaifish.tortoiserecordbook.bean.Tortoise;
import org.apache.ibatis.annotations.*;
import org.apache.ibatis.type.JdbcType;

import java.util.List;

/**
 * @description 乌龟mapper接口
 *
 * @author DEAiFISH
 * @date 2023/11/24 13:57
 */
public interface TortoiseMapper {
    /**
     * @description 根据用户ID查询持有的乌龟
     *
     * @author DEAiFISH
     * @date 2023/11/24 14:14
     * @param uid 用户id
     * @return com.deaifish.tortoiserecordbook.bean.Tortoise
     */
    @Results(id = "tortoiseMap", value = {
            @Result(column = "U_ID", property = "uid"),
            @Result(column = "T_ID", property = "id"),
            @Result(column = "T_NAME", property = "name"),
            @Result(column = "T_FIRST_TIME", property = "firstTime"),
            @Result(column = "T_HEAD_TILTS", property = "headTilts"),
            @Result(column = "T_MEMO", property = "memo"),
            @Result(column = "T_PRICE", property = "price"),

            /*
             *** typeHandler = MybatisEnumTypeHandler.class  用于将enum转换为int
             */
            @Result(column = "T_STATUS", property = "status", javaType = Status.class, jdbcType = JdbcType.INTEGER, typeHandler = MybatisEnumTypeHandler.class),
            @Result(column = "T_KIND", property = "kind", javaType = Kind.class, jdbcType = JdbcType.INTEGER, typeHandler = MybatisEnumTypeHandler.class),
    })
    @Select("select * from TORTOISE where U_ID = #{uid}")
    List<Tortoise> selByUID(String uid);

    /**
     * @description 根据乌龟ID查询乌龟信息
     *
     * @author DEAiFISH
     * @date 2023/11/24 17:05
     * @param tid 乌龟ID
     * @return com.deaifish.tortoiserecordbook.bean.Tortoise
     */
    @ResultMap("tortoiseMap")
    @Select("select * from TORTOISE where T_ID = #{tid}")
    Tortoise selByTID(String tid);

    /**
     * @description 查询所有乌龟
     *
     * @author DEAiFISH
     * @date 2023/11/24 14:15
     * @return java.util.List<com.deaifish.tortoiserecordbook.bean.Tortoise>
     */
    @ResultMap("tortoiseMap")
    @Select("select * from TORTOISE ORDER BY U_ID")
    List<Tortoise> selAll();

    /**
     * @description 修改乌龟信息
     *
     * @author DEAiFISH
     * @date 2023/11/24 14:32
     * @param tortoise 乌龟信息
     */
    @ResultMap("tortoiseMap")
    @Update("update TORTOISE set " +
            "T_NAME = #{name}," +
            "T_FIRST_TIME = #{firstTime}," +
            "T_HEAD_TILTS = #{headTilts}," +
            "T_STATUS = #{status}," +
            "T_KIND = #{kind}," +
            "T_MEMO = #{memo}," +
            "T_PRICE = #{price} " +
            "where T_ID = #{id}")
    void updTortoise(Tortoise tortoise);

    /**
     * @description 添加乌龟
     *
     * @author DEAiFISH
     * @date 2023/11/24 14:32
     * @param tortoise 乌龟信息
     */
    @ResultMap("tortoiseMap")
    @Insert("insert into TORTOISE (U_ID, T_ID, T_NAME, T_FIRST_TIME, T_STATUS, T_KIND, T_MEMO, T_PRICE,T_HEAD_TILTS)" +
            " values (#{uid},#{id},#{name},#{firstTime},#{status},#{kind},#{memo},#{price},#{headTilts});")
    void addTortoise(Tortoise tortoise);

    /**
     * @description 删除乌龟
     *
     * @author DEAiFISH
     * @date 2023/11/24 14:33
     * @param tId 乌龟ID
     */
    @Delete("delete from TORTOISE where T_ID = #{tid}")
    void delTortoise(String tId);
}
