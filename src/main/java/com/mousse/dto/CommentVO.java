package com.mousse.dto;

import com.mousse.entity.Comment;
import com.mousse.entity.User;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

/**
 * @author mousse
 * @data 2021/9/7
 */
@Data
@AllArgsConstructor
@NoArgsConstructor
public class CommentVO {

    private Comment comment;
    private User user;
}
