package com.example.board.common.error;

import lombok.Getter;

@Getter 
public class ApiException extends RuntimeException{
    private ExceptionEnum error;

    public ApiException(ExceptionEnum e, String messsage) {
        super(messsage);
        this.error = e;
    }

    public ApiException(ExceptionEnum e) {
        super(e.getMessage());
        this.error = e;
    }
}
