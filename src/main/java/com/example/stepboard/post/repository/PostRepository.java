package com.example.stepboard.post.repository;

import java.util.List;
import java.util.Optional;

import org.apache.ibatis.session.SqlSession;
import org.springframework.stereotype.Repository;

import com.example.stepboard.post.common.error.ApiException;
import com.example.stepboard.post.common.error.ExceptionEnum;
import com.example.stepboard.post.model.Board;
import com.example.stepboard.post.model.Post;

import lombok.RequiredArgsConstructor;

@Repository 
@RequiredArgsConstructor 
public class PostRepository {
    private final SqlSession sqlSession;
    private final String NAMESPACE = "post.";
    
    public long save(Post post) {
        int affectedRows = sqlSession.insert(NAMESPACE + "insert", post);
        
        if(affectedRows != 1) {
            throw new IllegalStateException("게시글 등록 결과가 올바르지 않습니다.");
        }
        return post.getPostId();
    }

    public Optional<Post> findById(long postId) {
        Post post = sqlSession.selectOne(NAMESPACE + "findById", postId);
        return Optional.ofNullable(post);
    }

    public boolean update(Post post) {
        int resultCount = sqlSession.update(NAMESPACE + "update", post);
        return resultCount == 1;
    }

    public boolean delete(long postId) {
        return sqlSession.delete(NAMESPACE + "deleteById", postId) == 1;
    }

    public List<Post> findAll() {
        return sqlSession.selectList(NAMESPACE + "findAll");
    }
}
