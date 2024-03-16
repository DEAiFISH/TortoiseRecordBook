package com.deaifish.tortoiserecordbook.service;

import com.deaifish.tortoiserecordbook.bean.User;
import com.deaifish.tortoiserecordbook.mapper.UserMapper;
import jakarta.annotation.Resource;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsPasswordService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.security.provisioning.UserDetailsManager;
import org.springframework.stereotype.Component;

import java.util.ArrayList;
import java.util.Collection;

/**
 * @description 自定义基于数据库的用户登录
 *
 * @author DEAiFISH
 * @date 2024/3/15 20:46
 */
@Component
public class DBUserDetailsManager implements UserDetailsManager, UserDetailsPasswordService {
    @Resource
    UserMapper userMapper;

    /**
     * @description 从数据中获取用户信息
     *
     * @author DEAiFISH
     * @date 2024/3/15 20:47
     * @param account
     * @return org.springframework.security.core.userdetails.UserDetails
     */
    @Override
    public UserDetails loadUserByUsername(String account) throws UsernameNotFoundException {
        User user = userMapper.selByAccount(account);
        if (user == null) {
            throw new UsernameNotFoundException(account);
        } else {
            Collection<GrantedAuthority> authorities = new ArrayList<>();  // 权限信息

            return new org.springframework.security.core.userdetails.User(
                    user.getAccount(),
                    user.getPasswd(),
                    true,
                    true, //用户账号是否过期
                    true, //用户凭证是否过期
                    true, //用户是否未被锁定
                    authorities); //权限列表
        }
    }

    @Override
    public UserDetails updatePassword(UserDetails user, String newPassword) {
        userMapper.updatePassword(user.getUsername(), newPassword);
        return user;
    }

    /**
     * @description 向数据库中插入新的用户信息
     *
     * @author DEAiFISH
     * @date 2024/3/15 21:20
     * @param userDetails
     */
    @Override
    public void createUser(UserDetails userDetails) {
    }

    @Override
    public void updateUser(UserDetails user) {
    }

    @Override
    public void deleteUser(String username) {

    }

    @Override
    public void changePassword(String oldPassword, String newPassword) {

    }

    @Override
    public boolean userExists(String username) {
        return false;
    }
}