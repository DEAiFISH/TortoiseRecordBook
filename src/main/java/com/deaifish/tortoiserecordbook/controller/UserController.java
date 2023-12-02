package com.deaifish.tortoiserecordbook.controller;

import com.deaifish.tortoiserecordbook.bean.User;
import com.deaifish.tortoiserecordbook.dto.UserLoginDTO;
import com.deaifish.tortoiserecordbook.dto.UserSingUpDTO;
import com.deaifish.tortoiserecordbook.dto.UserUpdateDTO;
import com.deaifish.tortoiserecordbook.properties.PathProperties;
import com.deaifish.tortoiserecordbook.service.IMGService;
import com.deaifish.tortoiserecordbook.service.UserService;
import jakarta.validation.constraints.NotEmpty;
import jakarta.validation.constraints.NotNull;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;
import java.util.List;

/**
 * @description 用户控制器
 *
 * @author DEAiFISH
 * @date 2023/11/22 23:44
 */
@RestController
@Slf4j
@RequestMapping("/user")
public class UserController {
    @Autowired
    UserService userService;
    @Autowired
    IMGService imgService;
    @Autowired
    PathProperties pathProperties;

    /**
     * @description 用户登录
     *
     * @author DEAiFISH
     * @date 2023/11/23 19:24
     * @param user 账号、密码
     * @return com.deaifish.tortoiserecordbook.bean.User
     */
    @PostMapping("/login")
    public User login(@RequestBody UserLoginDTO user) {
        return userService.login(user);
    }

    /**
     * @description 查询所有用户
     *
     * @author DEAiFISH
     * @date 2023/11/23 19:25

     * @return java.util.List<com.deaifish.tortoiserecordbook.bean.User>
     */
    @GetMapping("/search-all")
    public List<User> searchAll() {
        return userService.searchAll();
    }

    /**
     * @description 注册用户
     *
     * @author DEAiFISH
     * @date 2023/11/23 23:57
     * @param user 用户信息(包含头像文件路径)
     * @return java.lang.Boolean
     */
    @PostMapping("/signup")
    public boolean singUp(@Validated @RequestBody UserSingUpDTO user) {
        userService.signUp(User.builder().account(user.getAccount())
                .passwd(user.getPasswd())
                .headTilts(user.getHeadTilts()).build());
        return true;
    }

    /**
     * @description 上传头像
     *
     * @author DEAiFISH
     * @date 2023/11/27 17:40
     * @param file  头像文件
     * @return java.lang.String 文件路径
     */
    @PostMapping("/upload-photo")
    public String uploadHeadTilts(@NotNull(message = "文件不能为空。") @RequestParam MultipartFile file) throws IOException {
        return imgService.uploadImg(file.getInputStream(), pathProperties.userHeadTiltsDirPath);
    }

    /**
     * @description 修改用户信息
     *
     * @author DEAiFISH
     * @date 2023/11/27 15:40
     * @param user  用户信息（头像单独修改）
     * @return boolean
     */
    @PutMapping("/upd")
    public boolean updUser(@Validated @RequestBody UserUpdateDTO user) {
        User u = userService.selByID(user.getId());
        if (u == null) {
            return false;
        }
        userService.updUser(User.builder()
                .id(user.getId())
                .account(user.getAccount())
                .passwd(user.getPasswd())
                .headTilts(u.getHeadTilts())
                .build());
        return true;
    }

    /**
     * @description 修改头像
     *
     * @author DEAiFISH
     * @date 2023/11/27 17:37
     * @param img 头像文件
     * @param uid 用户ID
     * @return boolean
     */
    @PutMapping("/upd-photo")
    public String updatePhoto(@NotNull(message = "文件不能为空。") @RequestParam MultipartFile img, @Validated @RequestParam String uid) throws IOException {
        User user = userService.selByID(uid);
        String fileName = imgService.updImg(user.getHeadTilts(), img.getInputStream(), pathProperties.userHeadTiltsDirPath);
        user.setHeadTilts(fileName);
        userService.updUser(user);
        return fileName;
    }

    /**
     * @description 删除用户
     *
     * @author DEAiFISH
     * @date 2023/11/24 00:00
     * @param id 用户id
     * @return java.lang.Boolean
     */
    @DeleteMapping("/del")
    public boolean delUser(@NotEmpty @RequestParam String id) {
        userService.delUser(id);
        return true;
    }
}
