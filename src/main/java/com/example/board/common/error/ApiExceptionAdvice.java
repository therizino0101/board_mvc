package com.example.board.common.error;

import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestControllerAdvice;

import com.example.board.dto.ResponseDTO;

import jakarta.servlet.http.HttpServletRequest;

@RestControllerAdvice 
public class ApiExceptionAdvice {
    @ExceptionHandler ({ApiException.class})
    public ResponseDTO exceptionHandler(HttpServletRequest request, final ApiException e) {
        ResponseDTO responseDTO = new ResponseDTO();
        responseDTO.setResult(e.getError());
        responseDTO.setContent(null);
        responseDTO.setMessage(e.getMessage());
        return responseDTO;
    } 
}
