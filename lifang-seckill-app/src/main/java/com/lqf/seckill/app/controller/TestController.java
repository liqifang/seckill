package com.lqf.seckill.app.controller;

import cn.dev33.satoken.stp.StpUtil;
import com.lqf.seckill.common.aspect.ApiOperationLog;
import com.lqf.seckill.common.enums.ResponseCodeEnum;
import com.lqf.seckill.common.exception.BizException;
import com.lqf.seckill.common.utils.Response;
import lombok.extern.slf4j.Slf4j;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

@RestController
@Slf4j
public class TestController {

    /**
     * 测试公共返参 - 成功响应
     */
    @GetMapping("/test/response")
    @ApiOperationLog("测试公共返参")
    public Response<String> testResponse(@RequestParam String name) {
        return Response.success("Hello, " + name + " !");
    }

    /**
     * 测试业务异常捕获
     */
    @GetMapping("/test/bizException")
    @ApiOperationLog("测试业务异常捕获")
    public Response<String> testBizException() {
        // 模拟抛出业务异常
        throw new BizException(ResponseCodeEnum.PARAM_NOT_VALID);
    }

    /**
     * 测试系统异常捕获
     */
    @GetMapping("/test/systemException")
    @ApiOperationLog("测试系统异常捕获")
    public Response<String> testSystemException() {
        // 模拟抛出系统异常
        int i = 1 / 0;
        return Response.success("不会走到这里");
    }

    /**
     * 测试是否真的登录了
     */
    @GetMapping("/test/isLogin")
    public Response<?> isLogin() {
        // 调用 SaToken 提供的方法，判断当前请求是否已登录
        boolean isLogin = StpUtil.isLogin();

        if (isLogin) {
            // 已登录，获取当前登录的用户 ID
            long loginId = StpUtil.getLoginIdAsLong();
            log.info("==> 当前已登录, userId: {}", loginId);
            return Response.success("当前登录用户 ID: " + loginId);
        } else {
            // 未登录
            return Response.success("当前未登录");
        }
    }
}
