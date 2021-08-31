package com.mousse.config;

import com.mousse.exception.ICustomizeErrorCode;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.servlet.ModelAndView;

/**
 * @author mousse
 * @data 2021/8/30
 */
@ControllerAdvice
public class ExceptionConfig {

    @ExceptionHandler(Exception.class)
    ModelAndView handlerControllerException(Model model,Throwable e){
        if (e instanceof ICustomizeErrorCode) {
            model.addAttribute("message",e.getMessage());
        } else {
            model.addAttribute("message","服务出现了错误，请您返回首页");
        }
        return new ModelAndView("error");
    }


}
