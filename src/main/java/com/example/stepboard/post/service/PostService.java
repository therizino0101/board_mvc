package com.example.stepboard.post.service;

import java.util.List;

import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.example.stepboard.post.common.exception.PostNotFoundException;
import com.example.stepboard.post.dto.PageDTO;
import com.example.stepboard.post.model.Post;
import com.example.stepboard.post.model.PostRequest;
import com.example.stepboard.post.repository.PostRepository;

import lombok.RequiredArgsConstructor;

@Service 
@RequiredArgsConstructor 
public class PostService {

    private final PostRepository postRepository;

    @Transactional 
    public Post updatePost(long postId,
            PostRequest request) {
        Post post = new Post();
        post.setPostId(postId);
        post.setTitle(request.getTitle().strip());
        post.setContent(request.getContent().strip());
        
        if(!postRepository.update(post)) {
            throw new PostNotFoundException(postId);
        }
        return findById(postId);
    }

    @Transactional 
    public void deletePost(long postId) {
        if(!postRepository.delete(postId)) {
            throw new PostNotFoundException(postId);
        }
    }
    @Transactional(readOnly = true)
    public List<Post> getPosts(PageDTO pageDto) {
        
        return postRepository.findAll(pageDto);
    }
    
    private Post findById(long postId) {
        return postRepository.findById(postId)
            .orElseThrow(() -> new PostNotFoundException(postId));
    }
}
