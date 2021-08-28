package com.mousse.controller;

import com.mousse.entity.User;
import com.mousse.service.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;

import javax.servlet.http.Cookie;
import javax.servlet.http.HttpServletRequest;

@Controller
public class IndexController {

    @Autowired
    private UserService userService;

    @GetMapping("/")
    public String index(HttpServletRequest request) {

        Cookie[] cookies = request.getCookies();
        // 找出cookie中的token
        for (Cookie cookie : cookies) {
            if ("token".equals(cookie.getName())) {
                String value = cookie.getValue();
                // 找出token对应的User对象并存入session
                User user = userService.getByToken(value);
                request.getSession().setAttribute("user", user);
                break;
            }
        }
        return "index";
    }

}
