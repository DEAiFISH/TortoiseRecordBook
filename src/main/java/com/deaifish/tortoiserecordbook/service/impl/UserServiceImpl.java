package com.deaifish.tortoiserecordbook.service.impl;

import com.deaifish.tortoiserecordbook.bean.User;
import com.deaifish.tortoiserecordbook.exception.UserException;
import com.deaifish.tortoiserecordbook.mapper.UserMapper;
import com.deaifish.tortoiserecordbook.properties.PathProperties;
import com.deaifish.tortoiserecordbook.service.TortoiseService;
import com.deaifish.tortoiserecordbook.service.UserService;
import com.deaifish.tortoiserecordbook.utils.ImgUtil;
import com.deaifish.tortoiserecordbook.utils.UUIDUtil;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

/**
 * @description 用户服务
 *
 * @author DEAiFISH
 * @date 2023/11/22 23:01
 */
@Service
@Slf4j
public class UserServiceImpl implements UserService {

    @Autowired
    private UserMapper userMapper;
    @Autowired
    private TortoiseService tortoiseService;
    @Autowired
    private PathProperties pathProperties;
    @Autowired
    private ImgUtil imgUtil;
    @Autowired
    UUIDUtil uuidUtil;

    /**
     * @description 查询所有用户
     *
     * @author DEAiFISH
     * @date 2023/11/23 19:25

     * @return java.util.List<com.deaifish.tortoiserecordbook.bean.User>
     */
    @Override
    public List<User> searchAll() {
        return userMapper.selAll();
    }

    /**
     * @param user 用户信息
     * @description 注册用户
     * @author DEAiFISH
     * @date 2023/11/23 23:57
     */
    @Override
    public void signUp(User user) {
        user.setId(uuidUtil.getUUID());
        if (user.getHeadTilts() == null || user.getHeadTilts().isEmpty()) {
            user.setHeadTilts(imgUtil.getImgURL(pathProperties.userHeadTiltsDirPath + pathProperties.userDefaultPhotoName));
        }
        String encode = new BCryptPasswordEncoder().encode(user.getPasswd());
        user.setPasswd("{bcrypt}" + encode);
        userMapper.addUser(user);
    }

    /**
     * @description 根据用户ID查询用户信息
     *
     * @author DEAiFISH
     * @date 2023/11/27 15:42
     * @param id 用户ID
     * @return java.lang.String
     */
    @Override
    public User userExists(String id) {
        return userMapper.selByID(id);
    }

    /**
     * @description 修改用户信息
     *
     * @author DEAiFISH
     * @date 2023/11/25 10:02
     * @param user 用户信息
     */
    @Override
    public void updUser(User user) {
        userMapper.updUser(user);
    }

    /**
     * @param id 用户id
     * @description 删除用户, 先删除饲养记录，再删除乌龟信息，最后删除用户信息
     * @author DEAiFISH
     * @date 2023/11/24 00:00
     */
    @Override
    @Transactional  //开启事务
    public void delUser(String id) {
        User user = userMapper.selByID(id);
        if (user == null) {
            throw new UserException("用户ID不存在。");
        }
        //删除乌龟信息
        tortoiseService.delTortoiseByUID(id);
        // 删除头像
        imgUtil.delImg(user.getHeadTilts());
        userMapper.delUser(id);
    }
}
