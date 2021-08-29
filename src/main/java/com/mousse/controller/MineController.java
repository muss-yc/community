package com.mousse.controller;

import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.mousse.dto.UserDTO;
import com.mousse.entity.Question;
import com.mousse.entity.User;
import com.mousse.service.QuestionService;
import com.mousse.service.UserService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;

import javax.servlet.http.HttpServletRequest;
import java.util.Map;

/**
 * @author mousse
 * @data 2021/8/29
 */
@Controller
public class MineController {

    private static final Logger log = LoggerFactory.getLogger(MineController.class);
    @Autowired
    private QuestionService questionService;
    @Autowired
    private UserService userService;

    @RequestMapping("mine/{section}")
    public String section(@PathVariable("section") String section, Model model, HttpServletRequest request,
            @RequestParam(name = "current",defaultValue = "1")Long current,
            @RequestParam(name = "size",defaultValue = "2")Long size) {
        // 判断点击是否是最新动态
        model.addAttribute("section", section);
        if ("questions".equals(section)) model.addAttribute("sectionName","我的问题");
        if ("draft".equals(section)) model.addAttribute("sectionName","我的草稿");
        if ("collect".equals(section)) model.addAttribute("sectionName","我的收藏");

        User user = (User) request.getSession().getAttribute("user");
        Page<Question> page = new Page<>(current, size);
        Map<String,Object> map = questionService.listUserDTO(user.getId(), page);
        Long total = (Long) map.get("total");
        long pageCount = (total+size-1)/size;
        model.addAttribute("pageCount",pageCount);
        model.addAttribute("current",current);
        UserDTO userDTO = (UserDTO) map.get("userDTO");
        model.addAttribute("userDTO",userDTO);
        return "questions";
    }

}
