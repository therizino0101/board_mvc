package com.example.board.dto;

import lombok.Getter;
import lombok.ToString;

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
