package com.mousse.controller;

import com.mousse.entity.Question;
import com.mousse.entity.User;
import com.mousse.service.QuestionService;
import com.mousse.service.UserService;
import org.apache.commons.lang.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;

import javax.servlet.http.Cookie;
import javax.servlet.http.HttpServletRequest;
import java.util.Date;

@Controller
public class PublishController {

    @Autowired
    private QuestionService questionService;

    @Autowired
    private UserService userService;

    @GetMapping("/publish")
    public String publish() {

        return "publish";
    }

    @PostMapping("/publish")
    public String doPublish(HttpServletRequest request, Question question, Model model) {

        if (StringUtils.isBlank(question.getTitle()) || StringUtils.isBlank(question.getTag()) || StringUtils.isBlank(question.getDescription())) {
            model.addAttribute("question",question);
            model.addAttribute("error","确定标题，描述，标签是否填写！");
            return "publish";
        }

        Cookie[] cookies = request.getCookies();
        if (cookies!=null && cookies.length != 0){
            for (Cookie cookie : cookies) {
                if ("token".equals(cookie.getName())) {
                    String value = cookie.getValue();
                    User user = userService.getByToken(value);
                    question.setCreator(user.getId());
                    break;
                }
            }
        }
        question.setGmtCreate(new Date());
        question.setGmtModified(question.getGmtCreate());
        System.out.println(question);
        questionService.save(question);
        return "redirect:/";
    }

}
