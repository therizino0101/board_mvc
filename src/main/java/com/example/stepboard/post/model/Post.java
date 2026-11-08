package com.example.stepboard.post.model;

import java.time.LocalDateTime;

import lombok.Getter;
import lombok.Setter;

@Getter 
@Setter 
public class Post {
    private long postId;
    private String title;
    private String content;
    private LocalDateTime regDt;
    private LocalDateTime updDt;
    private String delYn;
    private LocalDateTime delDt;
}
