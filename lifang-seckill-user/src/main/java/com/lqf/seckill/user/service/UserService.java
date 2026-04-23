package com.lqf.seckill.user.service;

import com.lqf.seckill.common.utils.Response;
import com.lqf.seckill.user.model.vo.LoginUserReqVO;
import com.lqf.seckill.user.model.vo.LoginUserRspVO;
import com.lqf.seckill.user.model.vo.RegisterUserReqVO;
import com.lqf.seckill.user.model.vo.SendVerifyCodeReqVO;

public interface UserService {
    /**
     * 用户注册
     * @param registerUserReqVO
     * @return
     */
    Response<?> register(RegisterUserReqVO registerUserReqVO);

    /**
     * 用户登录
     * @param loginUserReqVO
     * @return
     */
    Response<LoginUserRspVO> login(LoginUserReqVO loginUserReqVO);

    /**
     * 发送验证码
     * @param sendVerifyCodeReqVO
     * @return
     */
    Response<?> sendVerifyCode(SendVerifyCodeReqVO sendVerifyCodeReqVO);

}
