package com.mousse.controller;

import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.mousse.dto.QuestionDTO;
import com.mousse.entity.Question;
import com.mousse.entity.User;
import com.mousse.service.QuestionService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;

import javax.servlet.http.HttpServletRequest;

/**
 * @author mousse
 * @data 2021/8/30
 */
@Controller
public class QuestionController {

    @Autowired
    private QuestionService questionService;

    @GetMapping("/question/{id}")
    public String question(@PathVariable("id") int id, HttpServletRequest request, Model model){

        // 设置浏览数
        Question question = questionService.getById(id);
        question.setViewCount(question.getViewCount()+1);
        QueryWrapper<Question> wrapper = new QueryWrapper<>();
        wrapper.eq("id",id);
        questionService.update(question, wrapper);
        QuestionDTO questionDTO = questionService.getQuestionDTOById(id);
        User user = (User) request.getSession().getAttribute("user");
        questionDTO.setUser(user);
        model.addAttribute("questionDTO",questionDTO);
        return "question";
    }

}
