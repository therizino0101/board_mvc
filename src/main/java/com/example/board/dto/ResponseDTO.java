package com.example.board.dto;

import lombok.Getter;
import lombok.Setter;

@Getter
@Setter 
public class ResponseDTO {
    private String result;
    private String message;
    private Object content;


    public ResponseDTO() {
    }
    public ResponseDTO(String result, String message) {
        this.result = result;
        this.message = message;
    }

    public void setResult(String result) {
        this.result = result;
    }

    public void setMessage(String message) {
        this.message = message;
    }
}
