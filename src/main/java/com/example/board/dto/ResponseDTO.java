package com.example.board.dto;

import com.example.board.common.error.ExceptionEnum;

import lombok.Getter;
import lombok.Setter;

@Getter
@Setter 
public class ResponseDTO {
    private ExceptionEnum result;
    private String message;
    private Object content;


    public ResponseDTO() {
    }
    public ResponseDTO(ExceptionEnum result, String message) {
        this.result = result;
        this.message = message;
    }

    public void setResult(ExceptionEnum result) {
        this.result = result;
    }

    public void setMessage(String message) {
        this.message = message;
    }
}
