package com.igrallery.jun.web.exception.handler;

import com.igrallery.jun.web.exception.AuthorizationException;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;

import javax.servlet.http.HttpServletResponse;
import java.io.IOException;

@ControllerAdvice
public class AuthorizationExceptionHandler {

    @ExceptionHandler(AuthorizationException.class)
    public void authorizationExceptionHandler (Exception e, HttpServletResponse response) throws IOException {
        response.sendRedirect("https://kauth.kakao.com/oauth/authorize?client_id=96679cb457a1d34481ca13d3259094c0&redirect_uri=http://localhost:8080/auth/kakao&response_type=code");
    }
}
