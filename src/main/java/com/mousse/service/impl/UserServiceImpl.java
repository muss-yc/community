package com.mousse.service.impl;

import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.mousse.entity.User;
import com.mousse.mapper.UserMapper;
import com.mousse.service.UserService;
import org.springframework.stereotype.Service;

@Service
public class UserServiceImpl extends ServiceImpl<UserMapper, User> implements UserService{
    @Override
    public User getByToken(String value) {
        return baseMapper.selectByToken(value);
    }

    @Override
    public User getByAccountId(String id) {
        return baseMapper.selectByAccountId(id);
    }
}
