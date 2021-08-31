package com.mousse.mapper;

import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.mousse.entity.User;


public interface UserMapper extends BaseMapper<User> {

    User selectByToken(String value);

    User selectByAccountId(String id);
}
