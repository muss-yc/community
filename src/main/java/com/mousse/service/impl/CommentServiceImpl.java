package com.mousse.service.impl;

import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.mousse.entity.Comment;
import com.mousse.entity.Question;
import com.mousse.enums.CommentTypeEnum;
import com.mousse.enums.CustomizeErrorCode;
import com.mousse.exception.CustomizeException;
import com.mousse.mapper.CommentMapper;
import com.mousse.service.CommentService;
import com.mousse.service.QuestionService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

/**
 * @author mousse
 * @data 2021/8/31
 */
@Service
public class CommentServiceImpl extends ServiceImpl<CommentMapper, Comment> implements CommentService {

    @Autowired
    private QuestionService questionService;

    @Transactional
    public void insert(Comment comment) {
        if (comment.getParentId() == 0) throw new CustomizeException(CustomizeErrorCode.TARGET_PARAM_NOT_FOUND);
        if (comment.getType() == null || !CommentTypeEnum.isExist(comment.getType())) throw new CustomizeException(CustomizeErrorCode.TYPE_PARAM_WRONG);
        if (comment.getType().equals(CommentTypeEnum.COMMENT.getType())) {
            // 回复评论
            Comment dbComment = baseMapper.selectById(comment.getId());
            if (dbComment == null) throw new CustomizeException(CustomizeErrorCode.COMMENT_NOT_FOUND);
        } else {
            // 回复问题
            Question question = questionService.getById(comment.getParentId());
            if (question == null) throw new CustomizeException(CustomizeErrorCode.QUESTION_NOT_FOUND);
        }
        baseMapper.insert(comment);
        questionService.updateCommentCountById(comment.getParentId());

    }

    @Override
    public List<Comment> getByParentId(int id) {
        List<Comment> commentList = baseMapper.selectBatchParentIds(id);
        if (commentList == null) throw new CustomizeException(CustomizeErrorCode.TARGET_PARAM_NOT_FOUND);
        return commentList;
    }

}
