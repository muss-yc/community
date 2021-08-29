package com.mousse.controller;

import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.mousse.dto.QuestionDTO;
import com.mousse.entity.Question;
import com.mousse.entity.User;
import com.mousse.service.QuestionService;
import com.mousse.service.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestParam;

import javax.servlet.http.Cookie;
import javax.servlet.http.HttpServletRequest;
import java.util.List;
import java.util.Map;

@Controller
public class IndexController {

    @Autowired
    private UserService userService;

    @Autowired
    private QuestionService questionService;

    @GetMapping("/")
    public String index(HttpServletRequest request, Model model,
            @RequestParam(name = "current",defaultValue = "1")Long current,
            @RequestParam(name = "size",defaultValue = "2")Long size) {

        Cookie[] cookies = request.getCookies();
        // 找出cookie中的token0
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
        return "index";
    }

}
