package com.human.business.config;

import cn.dev33.satoken.exception.NotLoginException;
import com.human.common.http.ResponseDTO;
import lombok.extern.slf4j.Slf4j;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestControllerAdvice;

@Slf4j
@RestControllerAdvice
public class GlobalExceptionHandler {

    @ExceptionHandler(MethodArgumentNotValidException.class)
    public ResponseDTO handleValidationException(MethodArgumentNotValidException e) {
        String message = e.getBindingResult().getFieldErrors().stream()
                .map(error -> error.getField() + ": " + error.getDefaultMessage())
                .reduce((a, b) -> a + "; " + b)
                .orElse("参数校验失败");
        return ResponseDTO.error(400L, message);
    }

    @ExceptionHandler(NotLoginException.class)
    public ResponseDTO handleNotLoginException(NotLoginException e) {
        return ResponseDTO.error(401L, "未登录或登录已过期，请重新登录");
    }

    @ExceptionHandler(Exception.class)
    public ResponseDTO handleException(Exception e) {
        log.error("系统异常", e);
        return ResponseDTO.error("系统繁忙，请稍后再试");
    }
}