package com.example.board.common.error;

import org.springframework.http.HttpStatus;

import lombok.Getter;
import lombok.Setter;
import lombok.ToString;

@Getter
public enum ExceptionEnum {

    SUCCESS(HttpStatus.OK, "성공"),
    FAIL(HttpStatus.INTERNAL_SERVER_ERROR, "실패");

    private HttpStatus status;
    private String message;


    ExceptionEnum(HttpStatus status, String message) {
        this.status = status;
        this.message = message;
    }

    public void setMessage(String message) {
        this.message = message;
    }



}
