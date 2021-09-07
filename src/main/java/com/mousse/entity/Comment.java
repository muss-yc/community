package com.mousse.entity;

import com.baomidou.mybatisplus.annotation.FieldStrategy;
import com.baomidou.mybatisplus.annotation.IdType;
import com.baomidou.mybatisplus.annotation.TableField;
import com.baomidou.mybatisplus.annotation.TableId;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.Date;

/**
 * @author mousse
 * @data 2021/8/31
 */
@Data
@AllArgsConstructor
@NoArgsConstructor
public class Comment {

    @TableId(type = IdType.AUTO)
    private int id;
    private int parentId;
    private int commentator;
    private int userId;
    private Date gmtCreate;
    private Date gmtModified;
    @TableField(insertStrategy = FieldStrategy.NEVER)
    private int likeCount;
    private String content;
    @TableField(exist = false)
    private Integer type;
}
