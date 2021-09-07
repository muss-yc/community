package com.mousse.service;

import com.baomidou.mybatisplus.extension.service.IService;
import com.mousse.entity.Comment;

import java.util.List;

/**
 * @author mousse
 * @data 2021/8/31
 */

public interface CommentService extends IService<Comment> {

    void insert(Comment comment);

    List<Comment> getByParentId(int id);
}
