package com.mousse.mapper;

import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.mousse.entity.Question;

public interface QuestionMapper extends BaseMapper<Question> {
    void updateViewCountById(int id);

    void updateCommentCountById(int parent_id);
}
