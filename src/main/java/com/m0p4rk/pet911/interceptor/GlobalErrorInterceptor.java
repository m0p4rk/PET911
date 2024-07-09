package com.m0p4rk.pet911.interceptor;

import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import org.springframework.web.servlet.HandlerInterceptor;
import org.springframework.web.servlet.ModelAndView;

public class GlobalErrorInterceptor implements HandlerInterceptor {

    @Override
    public void postHandle(HttpServletRequest request, HttpServletResponse response, Object handler, ModelAndView modelAndView) throws Exception {
        if (modelAndView != null) {
            Exception exception = (Exception) request.getAttribute("jakarta.servlet.error.exception");
            if (exception != null) {
                String errorMessage = exception.getMessage();
                modelAndView.addObject("error", errorMessage != null ? errorMessage : "알 수 없는 오류가 발생했습니다.");
                modelAndView.setViewName("error");
            }
        }
    }
}