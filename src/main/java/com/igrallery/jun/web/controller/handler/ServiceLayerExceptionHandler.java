package com.igrallery.jun.web.controller.handler;

import com.igrallery.jun.domain.exception.AuthorizationException;
import com.igrallery.jun.domain.exception.FileSaveException;
import com.igrallery.jun.domain.exception.GalleryNotFoundException;
import com.igrallery.jun.domain.exception.PermissionsException;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;

import javax.servlet.http.HttpServletResponse;
import java.io.IOException;

@ControllerAdvice
public class ServiceLayerExceptionHandler {

    @ExceptionHandler(AuthorizationException.class)
    public void authorizationExceptionHandler (Exception e, HttpServletResponse response) throws IOException {
        response.sendRedirect("https://kauth.kakao.com/oauth/authorize?client_id=96679cb457a1d34481ca13d3259094c0&redirect_uri=http://localhost:8080/auth/kakao&response_type=code");
    }

    @ExceptionHandler(PermissionsException.class)
    public void permissionsExceptionHandler (Exception e, HttpServletResponse response) throws IOException {
        response.sendError(HttpStatus.FORBIDDEN.value());
    }

    @ExceptionHandler(GalleryNotFoundException.class)
    public void galleryNotFoundExceptionHandler (Exception e, HttpServletResponse response) throws IOException {
        response.sendError(HttpStatus.NOT_FOUND.value());
    }

    @ExceptionHandler(FileSaveException.class)
    public void fileSaveExceptionHandler (RuntimeException e, HttpServletResponse response) throws IOException {
        response.sendError(HttpStatus.BAD_REQUEST.value());
    }
}
