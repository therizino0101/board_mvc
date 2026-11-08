package com.example.board.repository;

import org.apache.ibatis.session.SqlSession;
import org.springframework.stereotype.Repository;

import com.example.board.common.error.ApiException;
import com.example.board.common.error.ExceptionEnum;
import com.example.board.model.Board;
import com.example.board.model.Post;

import lombok.RequiredArgsConstructor;

@Repository 
@RequiredArgsConstructor 
public class PostRepository {
    private final SqlSession sqlSession;
    private final String NAMESPACE = "board.";
    
    public long save(Post post) {
        // 값을 저장하고 저장행수를 반환한다.
        // return: int The number of rows affected by the insert.
        int affectedRows = sqlSession.insert(NAMESPACE + "insertBoard", post);
        // 왜 IllegalStateException이지? 저장을 해야 정상 상태인데 저장하지 못해서 비정상상태라는거?
        // 근데 DB에서 저장을 못하는 경우가 있나? 앞에서 유효성 검증을 다해주는데..
        if(affectedRows != 1) {
            throw new IllegalStateException("게시글 등록 결과가 올바르지 않습니다.");
        }
        return post.getPostId();
    }

    public Post findById(long id) {
        Post post = sqlSession.selectOne(NAMESPACE + "findById", id);
        return post;
    }
}
