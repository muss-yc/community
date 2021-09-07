package com.mousse.mapper;

import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.mousse.entity.Comment;

import java.util.List;

/**
 * @author mousse
 * @data 2021/8/31
 */
public interface CommentMapper extends BaseMapper<Comment> {
    List<Comment> selectBatchParentIds(int id);
}
