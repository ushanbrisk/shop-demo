package com.shop.demo.service;

import com.shop.demo.model.dto.LoginDTO;
import com.shop.demo.model.dto.UserRegisterDTO;
import com.shop.demo.model.entity.User;
import com.shop.demo.model.vo.UserVO;

/**
 * 用户Service接口
 */
public interface UserService {

    /**
     * 用户注册
     *
     * @param dto 注册信息
     */
    void register(UserRegisterDTO dto);

    /**
     * 用户登录
     *
     * @param dto 登录信息
     * @return Token
     */
    String login(LoginDTO dto);

    /**
     * 根据ID查询用户
     *
     * @param id 用户ID
     * @return 用户信息
     */
    UserVO getUserById(Long id);

    /**
     * 根据用户名查询用户
     *
     * @param username 用户名
     * @return 用户信息
     */
    User getUserByUsername(String username);
}
