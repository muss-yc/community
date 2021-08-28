package com.mousse.mapper;

import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.mousse.entity.User;
import org.apache.ibatis.annotations.Param;


public interface UserMapper extends BaseMapper<User> {

    User selectByToken(@Param("token") String value);
}
