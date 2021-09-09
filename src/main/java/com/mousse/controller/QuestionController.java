package com.mousse.controller;

import com.mousse.dto.CommentVO;
import com.mousse.dto.QuestionDTO;
import com.mousse.entity.Comment;
import com.mousse.entity.Question;
import com.mousse.entity.User;
import com.mousse.service.CommentService;
import com.mousse.service.QuestionService;
import com.mousse.service.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;

import javax.servlet.http.HttpServletRequest;
import java.util.ArrayList;
import java.util.List;

/**
 * @author mousse
 * @data 2021/8/30
 */
@Controller
public class QuestionController {

    @Autowired
    private QuestionService questionService;
    @Autowired
    private UserService userService;
    @Autowired
    private CommentService commentService;

    @GetMapping("/question/{id}")
    public String question(@PathVariable("id") int id, HttpServletRequest request, Model model){

        // 设置浏览数
        questionService.updateViewCountById(id);
        QuestionDTO questionDTO = questionService.getQuestionDTOById(id);
        Integer userId = questionDTO.getQuestion().getCreator();
        User user = userService.getById(userId);
        questionDTO.setUser(user);
        model.addAttribute("questionDTO",questionDTO);
        // 设置问题回复列表
        List<Comment> commentList = commentService.getByParentId(id);
        List<CommentVO> commentVOList = new ArrayList<>();
        for (Comment comment : commentList) {
            CommentVO commentVO = new CommentVO();
            commentVO.setComment(comment);
            int commentUserId = comment.getUserId();
            User user1 = userService.getById(commentUserId);
            commentVO.setUser(user1);
            commentVOList.add(commentVO);
        }
        model.addAttribute("commentVOList",commentVOList);
        // 设置相关问题列表
        Question question = questionService.getById(id);
        String tag = question.getTag();
        String tags = tag.replace(",", "|");
        List<Question> questionList =  questionService.selectTagsRegular(tags,id);
        // 如果相关问题超出10个，便只取10个
        if (questionList.size() > 10) questionList = questionList.subList(0,10);
        model.addAttribute("questionList",questionList);
        return "question";
    }

}
