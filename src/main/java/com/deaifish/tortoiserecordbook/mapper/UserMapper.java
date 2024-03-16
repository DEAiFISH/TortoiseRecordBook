package com.deaifish.tortoiserecordbook.mapper;

import com.deaifish.tortoiserecordbook.bean.User;
import org.apache.ibatis.annotations.*;

import java.util.List;

/**
 * @description 操作用户数据库的mapper文件
 *
 * @author DEAiFISH
 * @date 2023/11/22 22:48
 */
public interface UserMapper {
    /**
     * @description 通过用户账号查询用户
     *
     * @author DEAiFISH
     * @date 2023/11/22 22:57
     * @return com.example.turtlerecordbook.bean.User
     */
    @Results(id = "userMap", value = {
            @Result(property = "id", column = "U_ID", id = true),
            @Result(property = "account", column = "U_ACCOUNT"),
            @Result(property = "passwd", column = "U_PASSWD"),
            @Result(property = "headTilts", column = "U_HEAD_TILTS")
    })
    @Select("select * from USER where U_ACCOUNT = #{account}")
    User selByAccount(String account);

    /**
     * @description 通过用户ID查询用户
     *
     * @author DEAiFISH
     * @date 2023/11/27 11:14
     * @param id 通过用户ID查询用户
     * @return com.deaifish.tortoiserecordbook.bean.User
     */
    @ResultMap("userMap")
    @Select("select * from USER where U_ID = #{id}")
    User selByID(String id);

    /**
     * @description 查询所有用户
     *
     * @author DEAiFISH
     * @date 2023/11/22 23:00
     * @return com.example.turtlerecordbook.bean.User
     */
    @ResultMap("userMap")
    @Select("select * from USER ORDER BY U_ID")
    List<User> selAll();

    /**
     * @description 注册用户
     *
     * @author DEAiFISH
     * @date 2023/11/23 17:05
     * @param user 用户信息
     */
    @ResultMap("userMap")
    @Insert("insert into USER(U_ID, U_ACCOUNT, U_PASSWD, U_HEAD_TILTS) " +
            "values (#{id},#{account},#{passwd},#{headTilts})")
    void addUser(User user);

    /**
     * @description 修改用户信息
     *
     * @author DEAiFISH
     * @date 2023/11/23 19:25
     * @param user 用户信息
     */
    @Update("update USER set U_ACCOUNT = #{account}," +
            "U_PASSWD = #{passwd}," +
            "U_HEAD_TILTS = #{headTilts} " +
            "where U_ID = #{id};")
    @ResultMap("userMap")
    void updUser(User user);

    /**
     * @description 删除用户
     *
     * @author DEAiFISH
     * @date 2023/11/23 23:49
     * @param id 用户ID
     */
    @Delete("delete from USER where U_ID = #{id}")
    void delUser(String id);

    /**
     * @description 修改用户密码
     *
     * @author DEAiFISH
     * @date 2024/3/16 20:14
     * @param account   用户账号
     * @param passwd    新密码
     */
    @Update("update USER set U_PASSWD = #{passwd} where U_ACCOUNT = #{account};")
    void updatePassword(String account, String passwd);
}
