package com.example.board.common.error;

import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestControllerAdvice;

import com.example.board.dto.ResponseDTO;


// 특정 Exception이 발생할때마다 처리하는 로직
// 매번 Exception 처리방식이 비슷한데, 이걸 사용하면
// 로직에는 throw 만하면돼서 간편해진다.
@RestControllerAdvice 
public class ApiExceptionAdvice {
    @ExceptionHandler ({ApiException.class})
    public ResponseDTO exceptionHandler(final ApiException e) {
        ResponseDTO responseDTO = new ResponseDTO();
        responseDTO.setResult(e.getError());
        responseDTO.setContent(null);
        responseDTO.setMessage(e.getMessage());
        return responseDTO;
    } 
}
