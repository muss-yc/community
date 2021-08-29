package com.mousse.dto;

import com.mousse.entity.Question;
import com.mousse.entity.User;
import lombok.Data;

import java.util.List;

/**
 * @author mousse
 * @data 2021/8/29
 */
@Data
public class UserDTO {
    private User user;
    private List<Question> questions;
}
