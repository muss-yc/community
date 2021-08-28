package com.mousse.service;

import com.baomidou.mybatisplus.extension.service.IService;

import com.mousse.dto.QuestionDTO;
import com.mousse.entity.Question;

import java.util.List;


public interface QuestionService extends IService<Question> {

    List<QuestionDTO> listQuestionDTO();
}
