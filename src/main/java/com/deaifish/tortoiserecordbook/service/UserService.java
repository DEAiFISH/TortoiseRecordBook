package com.deaifish.tortoiserecordbook.service;

import com.deaifish.tortoiserecordbook.bean.User;
import com.deaifish.tortoiserecordbook.dto.UserLoginDTO;

import java.util.List;

/**
 * @description 用户服务
 *
 * @author DEAiFISH
 * @date 2023/11/22 23:16
 */
public interface UserService {
    User login(UserLoginDTO user);

    List<User> searchAll();

    void signUp(User user);

    User selByID(String id);

    void updUser(User user);

    void delUser(String id);
}
