package com.mousse.controller;

import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.mousse.dto.TagCache;
import com.mousse.entity.Question;
import com.mousse.entity.User;
import com.mousse.service.QuestionService;
import com.mousse.service.UserService;
import org.apache.commons.lang.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;

import javax.servlet.http.HttpServletRequest;
import java.util.Arrays;
import java.util.Date;

@Controller
public class PublishController {

    @Autowired
    private QuestionService questionService;

    @Autowired
    private UserService userService;

    @GetMapping("/publish/{id}")
    public String edit(@PathVariable("id")int id,Model model){
        Question question = questionService.getById(id);
        model.addAttribute("tags",TagCache.get());
        model.addAttribute("question",question);
        return "publish";
    }

    @GetMapping("/publish")
    public String publish(Model model) {
        model.addAttribute("tags",TagCache.get());
        return "publish";
    }

    @PostMapping("/publish")
    public String doPublish(HttpServletRequest request, Question question, Model model) {

        model.addAttribute("tags",TagCache.get());

        if (StringUtils.isBlank(question.getTitle()) || StringUtils.isBlank(question.getTag()) || StringUtils.isBlank(question.getDescription())) {
            model.addAttribute("question",question);
            model.addAttribute("error","确定标题，描述，标签是否填写！");
            return "publish";
        }
        String tag = question.getTag();
        if ((tag.length()-1) == tag.lastIndexOf(",")) tag = tag.substring(0,tag.length()-1);
        String[] strings = TagCache.filterIllegal(tag);
        if (strings.length > 0) {
            model.addAttribute("error","存在非法字符"+ Arrays.toString(strings));
            return "publish";
        }
        if (question.getId() != null) {
            QueryWrapper<Question> wrapper = new QueryWrapper<>();
            wrapper.eq("id",question.getId());
            questionService.update(question,wrapper);
        }else {
            User user = (User) request.getSession().getAttribute("user");
            question.setTag(question.getTag().toLowerCase());
            question.setCreator(user.getId());
            question.setGmtCreate(new Date());
            question.setGmtModified(question.getGmtCreate());
            System.out.println(question);
            questionService.save(question);
        }
        return "redirect:/";
    }

}
