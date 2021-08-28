package com.mousse.controller;

import com.mousse.dto.QuestionDTO;
import com.mousse.entity.User;
import com.mousse.service.QuestionService;
import com.mousse.service.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;

import javax.servlet.http.Cookie;
import javax.servlet.http.HttpServletRequest;
import java.util.List;

@Controller
public class IndexController {

    @Autowired
    private UserService userService;

    @Autowired
    private QuestionService questionService;

    @GetMapping("/")
    public String index(HttpServletRequest request, Model model) {

        Cookie[] cookies = request.getCookies();
        // 找出cookie中的token。
        if (cookies != null && cookies.length != 0) {
            for (Cookie cookie : cookies) {
                if ("token".equals(cookie.getName())) {
                    String value = cookie.getValue();
                    // 找出token对应的User对象并存入session
                    User user = userService.getByToken(value);
                    request.getSession().setAttribute("user", user);
                    break;
                }
            }
        }
        // 调用service层获取QuestionDTO类型的List集合
        List<QuestionDTO> questionDTOS  = questionService.listQuestionDTO();
        model.addAttribute("questions",questionDTOS);
        return "index";
    }

}
