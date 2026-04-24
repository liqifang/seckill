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
}
