package com.example.board.dto;

import lombok.Getter;
import lombok.ToString;

/*
   API랑 주고받는 용도로 쓰는 객체
*/
@Getter 
@ToString 
public class BoardDTO {
    // id
    private String id;
    // 제목
    private String title;
    // 본문
    private String content;
}
