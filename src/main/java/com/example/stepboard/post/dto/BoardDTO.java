package com.example.stepboard.post.dto;

import jakarta.validation.constraints.NotBlank;
import lombok.Getter;

/*
   API랑 주고받는 용도로 쓰는 객체
*/
@Getter 
public class BoardDTO {
    // id
    private String id;
    // 제목
    @NotBlank(message = "제목은 필수 입력값입니다.")
    private String title;
    // 본문
    @NotBlank(message = "본문은 필수 입력값입니다.")
    private String content;
}
