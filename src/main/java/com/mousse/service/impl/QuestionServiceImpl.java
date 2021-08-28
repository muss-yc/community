package com.mousse.service.impl;

import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.mousse.dto.QuestionDTO;
import com.mousse.entity.Question;
import com.mousse.entity.User;
import com.mousse.mapper.QuestionMapper;
import com.mousse.service.QuestionService;
import com.mousse.service.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;

@Service
public class QuestionServiceImpl extends ServiceImpl<QuestionMapper, Question> implements QuestionService {

    @Autowired
    private UserService userService;

    public List<QuestionDTO> listQuestionDTO() {
        List<Question> questions = baseMapper.selectList(null);
        List<QuestionDTO> listQuestionDTO = new ArrayList<>();
        for (Question question : questions) {
            QuestionDTO questionDTO = new QuestionDTO();
            questionDTO.setQuestion(question);
            questionDTO.setUser(userService.getById(question.getCreator()));
            listQuestionDTO.add(questionDTO);
        }
        return listQuestionDTO;
    }
}
