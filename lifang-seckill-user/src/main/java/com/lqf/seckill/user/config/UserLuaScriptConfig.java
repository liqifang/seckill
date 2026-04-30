package com.lqf.seckill.user.config;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.core.io.ClassPathResource;
import org.springframework.data.redis.core.script.DefaultRedisScript;

/**
 * 将编写的lua脚本定义为Bean
 */
@Configuration
public class UserLuaScriptConfig {

    /**
     * 检查并删除验证码
     * @return
     */
    @Bean
    public DefaultRedisScript<Long> checkAndDelVerifyCodeScript() {
        DefaultRedisScript<Long> script = new DefaultRedisScript<>();
        script.setLocation(new ClassPathResource("lua/check_and_delete_verify_code.lua"));
        script.setResultType(Long.class);
        return script;
    }

    /**
     * 检查登录失败次数并累加
     * @return
     */
    @Bean
    public DefaultRedisScript<Long> checkAndIncrementLoginFailScript() {
        DefaultRedisScript<Long> script = new DefaultRedisScript<>();
        script.setLocation(new ClassPathResource("lua/check_and_increment_login_fail_count.lua"));
        script.setResultType(Long.class);
        return script;
    }

    /**
     * 检查每日验证码发送次数并累加
     * @return 如果已达上限，返回 -1；如果未达上限，累加发送次数，并返回累加后的值
     */
    @Bean
    public DefaultRedisScript<Long> checkAndIncrementVerifyCodeDailyLimitScript() {
        DefaultRedisScript<Long> script = new DefaultRedisScript<>();
        script.setLocation(new ClassPathResource("lua/check_and_increment_verify_code_daily_limit.lua"));
        script.setResultType(Long.class);
        return script;
    }
}
