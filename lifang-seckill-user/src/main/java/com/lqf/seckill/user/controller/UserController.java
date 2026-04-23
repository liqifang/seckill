package com.lqf.seckill.user.controller;

import com.lqf.seckill.common.aspect.ApiOperationLog;
import com.lqf.seckill.common.utils.Response;
import com.lqf.seckill.user.model.vo.LoginUserReqVO;
import com.lqf.seckill.user.model.vo.LoginUserRspVO;
import com.lqf.seckill.user.model.vo.RegisterUserReqVO;
import com.lqf.seckill.user.service.UserService;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/user")
@Slf4j
@RequiredArgsConstructor
public class UserController {

    private final UserService userService;

    /**
     * 用户注册
     */
    @PostMapping("/register")
    @ApiOperationLog("用户注册")
    public Response<?> register(@Validated @RequestBody RegisterUserReqVO registerUserReqVO) {
        return userService.register(registerUserReqVO);
    }

    /**
     * 用户登录
     */
    @PostMapping("/login")
    @ApiOperationLog("用户登录")
    public Response<LoginUserRspVO> login(@Validated @RequestBody LoginUserReqVO loginUserReqVO) {
        return userService.login(loginUserReqVO);
    }
}