package com.mousse.controller;

import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.mousse.dto.QuestionDTO;
import com.mousse.entity.Question;
import com.mousse.service.QuestionService;
import com.mousse.service.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestParam;

import java.util.List;
import java.util.Map;

@Controller
public class IndexController {

    @Autowired
    private UserService userService;

    @Autowired
    private QuestionService questionService;

    @GetMapping("/")
    public String index(Model model,
            @RequestParam(name = "current",defaultValue = "1")Long current,
            @RequestParam(name = "size",defaultValue = "4")Long size){
        Page<Question> page = new Page<>(current, size);
        // 调用service层获取QuestionDTO类型的List集合
        Map<String, Object> map = questionService.listQuestionDTO(page);
        List<QuestionDTO> questionList = (List<QuestionDTO>) map.get("questionList");
        // 计算总页数
        Long total = (Long) map.get("total");
        long pageCount = (total+size-1)/size;
        model.addAttribute("questions",questionList);
        model.addAttribute("pageCount",pageCount);
        model.addAttribute("current",current);
        // 获取热门话题
        List<Question> hotTopic  =  questionService.selectQuestionListByViewCount();
        model.addAttribute("hotTopic",hotTopic);
        return "index";
    }

}
