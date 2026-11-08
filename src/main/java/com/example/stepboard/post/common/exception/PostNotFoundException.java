package com.example.stepboard.post.common.exception;

public class PostNotFoundException extends RuntimeException{
    
    public PostNotFoundException(long postId) {
        super("게시글 번호를 찾을 수 없습니다. 게시글 번호: " + postId);
    }
}
