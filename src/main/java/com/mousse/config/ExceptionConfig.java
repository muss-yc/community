package com.mousse.config;

import com.mousse.dto.Result;
import com.mousse.enums.CustomizeErrorCode;
import com.mousse.exception.CustomizeException;
import com.mousse.exception.ICustomizeErrorCode;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.servlet.ModelAndView;
import org.springframework.web.servlet.view.json.MappingJackson2JsonView;

import javax.servlet.http.HttpServletRequest;

/**
 * @author mousse
 * @data 2021/8/30
 */
@ControllerAdvice
public class ExceptionConfig {

    @ExceptionHandler(Exception.class)
    ModelAndView handlerControllerException(Model model, Throwable e, HttpServletRequest request) {
        ModelAndView mv = new ModelAndView();
        // 返回json
        if ((Boolean) request.getAttribute("method_return_is_json")) {
            mv.setView(new MappingJackson2JsonView());
            Result result;
            if (e instanceof ICustomizeErrorCode) {
                result = Result.fail((CustomizeException) e);
            } else {
                result = Result.fail(CustomizeErrorCode.SYS_ERROR);
            }
            mv.addObject(result);
        } else { //返回页面
            if (e instanceof ICustomizeErrorCode) {
                model.addAttribute("message", e.getMessage());
            } else {
                model.addAttribute("message", CustomizeErrorCode.SYS_ERROR.getMessage());
            }
            mv.setViewName("error");
        }
        return mv;
    }


}
