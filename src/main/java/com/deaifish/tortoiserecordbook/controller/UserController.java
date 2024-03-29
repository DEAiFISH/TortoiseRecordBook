package com.deaifish.tortoiserecordbook.controller;

import com.deaifish.tortoiserecordbook.bean.User;
import com.deaifish.tortoiserecordbook.dto.UserSingUpDTO;
import com.deaifish.tortoiserecordbook.dto.UserUpdateDTO;
import com.deaifish.tortoiserecordbook.properties.PathProperties;
import com.deaifish.tortoiserecordbook.service.IMGService;
import com.deaifish.tortoiserecordbook.service.UserService;
import com.deaifish.tortoiserecordbook.vo.ResultVO;
import jakarta.validation.constraints.NotEmpty;
import jakarta.validation.constraints.NotNull;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
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
@CrossOrigin()    //解决跨域问题
public class UserController {
    @Autowired
    UserService userService;
    @Autowired
    IMGService imgService;
    @Autowired
    PathProperties pathProperties;


    /**
     * @description 查询所有用户
     *
     * @author DEAiFISH
     * @date 2023/11/23 19:25

     * @return java.util.List<com.deaifish.tortoiserecordbook.bean.User>
     */
    @GetMapping("/search-all")
    public ResultVO<List<User>> searchAll() {
        return new ResultVO<>(HttpStatus.OK.value(), "查询所有用户", userService.searchAll());
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
    public ResultVO<String> singUp(@Validated @RequestBody UserSingUpDTO user) {
        userService.signUp(User.builder().account(user.getAccount())
                .passwd(user.getPasswd())
                .headTilts(user.getHeadTilts()).build());
        return new ResultVO<>(HttpStatus.OK.value(), "注册用户", "注册成功。");
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
    public ResultVO<String> uploadHeadTilts(@NotNull(message = "文件不能为空。") @RequestParam MultipartFile file) throws IOException {
        String path = imgService.uploadImg(file.getInputStream(), pathProperties.userHeadTiltsDirPath);
        return new ResultVO<>(HttpStatus.OK.value(), "上传头像", path);
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
    public ResultVO<String> updUser(@Validated @RequestBody UserUpdateDTO user) {
        User u = userService.userExists(user.getId());
        if (u == null) {
            return new ResultVO<>(HttpStatus.OK.value(), "修改用户信息", "用户不存在。");
        }
        userService.updUser(User.builder()
                .id(user.getId())
                .account(user.getAccount())
                .passwd("{bcrypt}" + new BCryptPasswordEncoder().encode(user.getPasswd()))
                .headTilts(u.getHeadTilts())
                .build());
        return new ResultVO<>(HttpStatus.OK.value(), "修改用户信息", "修改成功。");
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
    public ResultVO<String> updatePhoto(@NotNull(message = "文件不能为空。") @RequestParam MultipartFile img, @Validated @RequestParam String uid) throws IOException {
        User user = userService.userExists(uid);
        String fileName = imgService.updImg(user.getHeadTilts(), img.getInputStream(), pathProperties.userHeadTiltsDirPath);
        user.setHeadTilts(fileName);
        userService.updUser(user);
        return new ResultVO<>(HttpStatus.OK.value(), "修改头像", fileName);
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
    public ResultVO<String> delUser(@NotEmpty @RequestParam String id) {
        userService.delUser(id);
        return new ResultVO<>(HttpStatus.OK.value(), "删除用户", "删除成功。");
    }

    /**
     * @description 查询ID是否被使用
     *
     * @author DEAiFISH
     * @date 2023/12/6 23:25
     * @param id 用户ID
     * @return Boolean true:被占用;false:未被占用
     */
    @GetMapping("/check")
    public ResultVO<Boolean> checkID(@RequestParam String id) {
        User user = userService.userExists(id);
        return new ResultVO<>(HttpStatus.OK.value(), "查询ID是否被注册。", user != null);
    }
}
