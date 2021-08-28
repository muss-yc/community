package com.mousse.dto;


import com.mousse.entity.Question;
import com.mousse.entity.User;
import lombok.Data;



@Data
public class QuestionDTO {

    private Question question;
    private User user;
}
