package com.mousse.service.impl;

import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.mousse.dto.QuestionDTO;
import com.mousse.entity.Question;
import com.mousse.mapper.QuestionMapper;
import com.mousse.service.QuestionService;
import com.mousse.service.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

@Service
public class QuestionServiceImpl extends ServiceImpl<QuestionMapper, Question> implements QuestionService {

    @Autowired
    private UserService userService;

    public Map<String,Object> listQuestionDTO(Page<Question> page) {
        Page<Question> questionPage = baseMapper.selectPage(page, null);
        List<Question> questions = questionPage.getRecords();
        // 获取所在页，总页数
        long current = questionPage.getCurrent();
        long total = questionPage.getTotal();
        HashMap<String, Object> map = new HashMap<>();
        map.put("current",current);
        map.put("total",total);
        // 将Question类型的List集合封装到QuestionDTO类型的List集合中
        List<QuestionDTO> listQuestionDTO = new ArrayList<>();
        for (Question question : questions) {
            QuestionDTO questionDTO = new QuestionDTO();
            questionDTO.setQuestion(question);
            questionDTO.setUser(userService.getById(question.getCreator()));
            listQuestionDTO.add(questionDTO);
        }
        map.put("questionList",listQuestionDTO);
        return map;
    }
}
