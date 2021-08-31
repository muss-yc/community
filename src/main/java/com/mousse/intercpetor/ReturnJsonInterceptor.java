package com.mousse.intercpetor;

import org.jetbrains.annotations.NotNull;
import org.springframework.stereotype.Component;
import org.springframework.web.servlet.HandlerInterceptor;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

/**
 * 判断是返回json数据还是返回页面
 * @author mousse
 * @data 2021/8/31
 */
@Component
public class ReturnJsonInterceptor implements HandlerInterceptor {
    @Override
    public boolean preHandle(@NotNull HttpServletRequest request, @NotNull HttpServletResponse response, @NotNull Object handler) throws Exception {

        String contentType = request.getContentType();
        if ("application/json".equals(contentType)) {
            request.setAttribute("method_return_is_json",true);
        } else {
            request.setAttribute("method_return_is_json",false);
        }
        return true;
    }
}
