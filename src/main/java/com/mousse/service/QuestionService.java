package com.mousse.service;

import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.baomidou.mybatisplus.extension.service.IService;
import com.mousse.entity.Question;

import java.util.Map;


public interface QuestionService extends IService<Question> {

    Map<String,Object> listQuestionDTO(Page<Question> page);
}
