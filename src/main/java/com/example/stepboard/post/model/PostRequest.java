package com.example.stepboard.post.model;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Size;
import lombok.Getter;


// Resolved [org.springframework.http.converter.HttpMessageNotReadableException: 
// JSON parse error: Unrecognized token 'GET': was expecting 
// (JSON String, Number, Array, Object or token 'null', 'true' or 'false')]
// 아..? 혹시 
// ould not resolve parameter [0] in public org.springframework.http.ResponseEntity<com.example.board.model.Board> com.example.board.controller.BoardController.createBoard(com.example.board.model.PostRequest):
// 위에 있는거 혹시 컨트롤러 return 타입이랑 메서드 이름, 받는 파라미터 풀네임으로 적은건가?
// 파라미터 불러오다가 에러가 났다?
//Unrecognized token GET? token GET이 뭘말하는거여..?
// String, Number, Array 같은 타입을 예측하고 있었는데 그게 아닌 값이 들어왔다이건가?
// 그렇다면 파라미터에 저장된 값을 GET으로 읽으려고 하는데 
// JSON parse를 해서 자바 객체로 만들때 Setter가 없어서 제대로 Set이 안된걸까?
@Getter 
// class < record (요청 객체라서 불변하기 때문)
public class PostRequest {
    @NotBlank(message = "제목을 입력해 주세요")
    @Size(max = 200, message = "제목은 200자 까지 입력할 수 있습니다")
    private String title;

    @NotBlank(message ="내용을 입력해 주세요")
    @Size (max = 10000, message = "내용은 10000자까지 입력할 수 있습니다.")
    private String content;
}
