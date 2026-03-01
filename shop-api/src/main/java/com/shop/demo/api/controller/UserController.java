package com.shop.demo.api.controller;

import com.shop.demo.common.Result;
import com.shop.demo.model.dto.LoginDTO;
import com.shop.demo.model.dto.UserRegisterDTO;
import com.shop.demo.model.vo.UserVO;
import com.shop.demo.service.UserService;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;

import javax.annotation.Resource;
import javax.validation.Valid;

/**
 * 用户Controller
 */
@RestController
@RequestMapping("/api/users")
public class UserController {

    @Resource
    private UserService userService;

    @PostMapping("/register")
    public Result<Void> register(@RequestBody @Valid UserRegisterDTO dto) {
        userService.register(dto);
        return Result.success();
    }

    @PostMapping("/login")
    public Result<String> login(@RequestBody @Valid LoginDTO dto) {
        String token = userService.login(dto);
        return Result.success(token);
    }

    @GetMapping("/{id}")
    public Result<UserVO> getUser(@PathVariable Long id) {
        return Result.success(userService.getUserById(id));
    }
}
