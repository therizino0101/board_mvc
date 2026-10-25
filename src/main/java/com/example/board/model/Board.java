package com.example.board.model;


import java.time.LocalDateTime;

import lombok.Getter;
import lombok.Setter;
/*
    DB의 테이블을 그대로 만든 모델DTO
*/
@Setter
@Getter
public class Board {
    // id
    // 상세조회할때 1개만 골라야 되는데 title을 쓰기에는 중복이 있을것 같아서 id를 만듬.
    private int id;
    // 제목
    private String title;
    // 본문

    private String content;
    // 작성일
    // 시분초까지 기록하기 위해서 사용
    private LocalDateTime regDt;
    //수정일
    // LocalDate 쓰니까 시분초가 기록이 안됨;;
    private LocalDateTime updDt;
    // 데이터를 지우면 삭제된 게시물 여부 확인을 할 수 없어서
    // 그냥 로직으로 soft하게 삭제처리 할 수 있도록 변경
    private String delYn;
    // 삭제일
    private LocalDateTime delDt;

}
