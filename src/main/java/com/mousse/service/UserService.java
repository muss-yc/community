package com.mousse.service;

import com.baomidou.mybatisplus.extension.service.IService;
import com.mousse.entity.User;

public interface UserService extends IService<User> {

    User getByToken(String value);

    User getByAccountId(String id);
}
