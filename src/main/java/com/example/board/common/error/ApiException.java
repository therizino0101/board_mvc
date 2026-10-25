package com.example.board.common.error;

import lombok.Getter;

/*
    게시판 서비스에 적합하게 Exception을 관리하기 위해 만든 커스텀 Exception
    ExceptionEnum을 사용해서 하드코딩된 상태나 메시지를 방지할 수 있다.
    RuntimeException을 사용한 이유는 실행중에 발생하는 에러라서 적합하다 판단했고,
    트렌잭션이 걸려있을경우 해당 트랜잭션을 취소하기 때문이다.
*/
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
