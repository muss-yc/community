package com.mousse.entity;

import com.baomidou.mybatisplus.annotation.IdType;
import com.baomidou.mybatisplus.annotation.TableId;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.Date;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class User {

    @TableId(type = IdType.AUTO)
    private int id;
    private String accountId;
    private String name;
    private String token;
    private Date gmtCreate;
    private Date gmtModified;
    private String avatarUrl;

    public User(String accountId, String name, String token, Date gmtCreate, Date gmtModified, String avatarUrl) {
        this.accountId = accountId;
        this.name = name;
        this.token = token;
        this.gmtCreate = gmtCreate;
        this.gmtModified = gmtModified;
        this.avatarUrl = avatarUrl;
    }
}
