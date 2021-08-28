package com.mousse.controller;

import com.mousse.dto.AccessTokenDTO;
import com.mousse.dto.GithubUser;
import com.mousse.entity.User;
import com.mousse.provider.GithubProvider;
import com.mousse.service.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestParam;
import javax.servlet.http.Cookie;
import javax.servlet.http.HttpServletResponse;
import java.util.Date;
import java.util.UUID;

@Controller
public class AuthorizeController {

    @Autowired
    private GithubProvider githubProvider;

    @Autowired
    private UserService userService;

    @Value("${oauth.client_id}")
    private String client_id;
    @Value("${oauth.client_secret}")
    private String client_secret;
    @Value("${oauth.redirect_url}")
    private String redirect_url;

    @GetMapping("/callback")
    public String callback(@RequestParam("code") String code,
            @RequestParam("state") String state,
            HttpServletResponse response) {

        // 设置AccessTokenDTO对象
        AccessTokenDTO accessTokenDTO = new AccessTokenDTO(client_id,client_secret,code,redirect_url,state);
        String accessToken = githubProvider.getAccessToken(accessTokenDTO);
        GithubUser githubUser = githubProvider.getUser(accessToken);
        if (githubUser != null) {
            // 登录成功后
            // 设置token
            String token = UUID.randomUUID().toString();
            // 设置User对象并保存到数据库中
            User user = new User(githubUser.getId(),githubUser.getName(),token,new Date(),new Date(),githubUser.getAvatar_url());
            userService.save(user);
            // 将token存入到cookie里面
            response.addCookie(new Cookie("token",token));
        }
        // 重定向到首页
        return "redirect:/";
    }

}
