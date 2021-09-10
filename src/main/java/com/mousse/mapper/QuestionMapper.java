package com.mousse.mapper;

import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.mousse.entity.Question;
import org.apache.ibatis.annotations.Param;

import java.util.List;

public interface QuestionMapper extends BaseMapper<Question> {
    void updateViewCountById(int id);

    void updateCommentCountById(int parent_id);

    List<Question> selectByTags(@Param("tags") String tags,@Param("id") int id);

    List<Question> selectQuestionListByViewCount();
}
