package com.example.board.model;

import java.time.LocalDate;

public class Board {
    // id
    private int id;
    // 제목
    private String title;
    // 본문
    private String content;
    // 작성일
    private LocalDate regDt;
    //수정일
    private LocalDate updDt;
    private String delYn;
    // 삭제일
    private LocalDate delDt;

}
