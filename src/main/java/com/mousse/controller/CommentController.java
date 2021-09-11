package com.mousse.controller;

import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.mousse.dto.CommentDTO;
import com.mousse.dto.CommentVO;
import com.mousse.dto.Result;
import com.mousse.entity.Comment;
import com.mousse.entity.User;
import com.mousse.enums.CustomizeErrorCode;
import com.mousse.exception.CustomizeException;
import com.mousse.service.CommentService;
import com.mousse.service.UserService;
import org.apache.commons.lang.StringUtils;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;

import javax.servlet.http.HttpServletRequest;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

/**
 * @author mousse
 * @data 2021/8/31
 */
@Controller
public class CommentController {

    @Autowired
    private CommentService commentService;
    @Autowired
    private UserService userService;

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
        if (commentDTO.getType() == 1) {
            Integer notifyCount = (Integer) request.getSession().getAttribute("notifyCount");
            if (notifyCount == null) {
                request.getSession().setAttribute("notifyCount",1);
            } else {
                request.getSession().setAttribute("notifyCount",notifyCount+1);
            }
            List<Comment> newComments = (List<Comment>) request.getSession().getAttribute("newComments");
            if (newComments == null) {
                request.getSession().setAttribute("newComments",new ArrayList<Comment>());
                newComments = (List<Comment>) request.getSession().getAttribute("newComments");
            }
            newComments.add(comment);
        }
        if (commentDTO.getType() == 2){
            int parentId = commentDTO.getParentId();
            Comment commentServiceById = commentService.getById(parentId);
            commentServiceById.setCommentCount(commentServiceById.getCommentCount()+1);
            QueryWrapper<Comment> wrapper = new QueryWrapper<>();
            wrapper.eq("id",parentId);
            commentService.update(commentServiceById,wrapper);
        }
        return Result.ok(comment);
    }

    @GetMapping("/comment/{id}")
    @ResponseBody
    public Result comments(@PathVariable("id")int id ){
        Comment commentServiceById = commentService.getById(id);
        if (commentServiceById == null) throw new CustomizeException(CustomizeErrorCode.COMMENT_NOT_FOUND);
        List<Comment> commentList = commentService.getByParentId(id);
        List<CommentVO> commentVOList = new ArrayList<>();
        for (Comment comment : commentList) {
            CommentVO commentVO = new CommentVO();
            commentVO.setComment(comment);
            int commentUserId = comment.getUserId();
            User user1 = userService.getById(commentUserId);
            commentVO.setUser(user1);
            commentVOList.add(commentVO);
        }
        return Result.ok(commentVOList);
    }

}
