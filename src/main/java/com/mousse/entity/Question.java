package com.mousse.entity;


import com.baomidou.mybatisplus.annotation.*;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.Date;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class Question {

    @TableId(type = IdType.AUTO)
    private int id;
    private String title;
    private String description;
    private Date gmtCreate;
    private Date gmtModified;
    private String tag;
    private Integer creator;
    @TableField(insertStrategy = FieldStrategy.NEVER)
    private Integer commentCount;
    @TableField(insertStrategy = FieldStrategy.NEVER)
    private Integer viewCount;
    @TableField(insertStrategy = FieldStrategy.NEVER)
    private Integer likeCount;

}
