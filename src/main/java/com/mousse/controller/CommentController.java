package com.mousse.controller;

import com.mousse.dto.Result;
import com.mousse.dto.CommentDTO;
import com.mousse.entity.Comment;
import com.mousse.entity.User;
import com.mousse.enums.CustomizeErrorCode;
import com.mousse.service.CommentService;
import org.apache.commons.lang.StringUtils;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.ResponseBody;

import javax.servlet.http.HttpServletRequest;
import java.util.Date;

/**
 * @author mousse
 * @data 2021/8/31
 */
@Controller
public class CommentController {

    @Autowired
    private CommentService commentService;

    @PostMapping("/comment")
    @ResponseBody
    public Object post(@RequestBody CommentDTO commentDTO, HttpServletRequest request) {

        User user = (User) request.getSession().getAttribute("user");
        if (user == null) return Result.fail(CustomizeErrorCode.NO_LOGIN);
        if (commentDTO == null || StringUtils.isBlank(commentDTO.getContent())) return Result.fail(CustomizeErrorCode.CONTENT_IS_EMPTY);
        Comment comment = new Comment();
        BeanUtils.copyProperties(commentDTO, comment);
        comment.setGmtCreate(new Date());
        comment.setGmtModified(new Date());
        comment.setCommentator(1);
        commentService.insert(comment);
        return Result.ok(comment);
    }

}
