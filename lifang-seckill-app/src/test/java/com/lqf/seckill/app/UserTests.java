package com.lqf.seckill.app;

import com.lqf.seckill.common.domain.dataobject.UserDO;
import com.lqf.seckill.common.domain.mapper.UserDOMapper;
import jakarta.annotation.Resource;
import org.junit.jupiter.api.Test;
import org.springframework.boot.test.context.SpringBootTest;

import java.time.LocalDateTime;

@SpringBootTest
public class UserTests {

    @Resource
    private UserDOMapper userDOMapeer;

    @Test
    void testMapper(){
        userDOMapeer.insert(
                UserDO.builder()
                        .nickname("李仿")
                        .password("123456")
                        .mobile("15149845307")
                        .avatar(null)
                        .status(1)
                        .createTime(LocalDateTime.now())
                        .updateTime(LocalDateTime.now())
                        .build());
    }
}
