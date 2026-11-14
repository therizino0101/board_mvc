package com.example.stepboard.post.controller;

import java.net.URI;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.springframework.http.ResponseEntity;
import org.springframework.validation.BindingResult;
import org.springframework.validation.FieldError;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.util.UriComponentsBuilder;

import com.example.stepboard.post.dto.BoardDTO;
import com.example.stepboard.post.dto.ResponseDTO;
import com.example.stepboard.post.model.Board;
import com.example.stepboard.post.model.Post;
import com.example.stepboard.post.model.PostRequest;
import com.example.stepboard.post.service.BoardService;
import com.example.stepboard.post.service.PostService;

import jakarta.validation.Valid;
import jakarta.validation.constraints.Min;
import lombok.RequiredArgsConstructor;

@RestController
@RequestMapping("/api/posts")
@RequiredArgsConstructor
@Validated 
public class PostController {
    
    private final BoardService boardService;
    private final PostService postService;

    @PostMapping
    public ResponseEntity<Post> createBoard(@Valid @RequestBody PostRequest request) {
       
        Post post = boardService.createPost(request);
        return ResponseEntity.created(URI.create("/api/posts/" + post.getPostId()))
            .body(post);
    }

    @GetMapping("/{postId}")
    public ResponseEntity<Post> getPost(
        @PathVariable @Min(value = 1, message ="게시글 번호는 1 이상이어야 합니다")long postId
    ) {
        return ResponseEntity.ok(boardService.getPost(postId));
    }
    

    @GetMapping
    public ResponseEntity<List<Post>> getPosts() {
        return ResponseEntity.ok(postService.getPosts());
    }

    @PutMapping("/{postId}")
    public ResponseEntity<Post> update(
        @PathVariable @Min(value = 1, message = "id값은 최소 1로 작성하세요.") long postId,
        @Valid @RequestBody PostRequest request
    ) {
        return ResponseEntity.ok(postService.updatePost(postId, request));
    }

  
    @DeleteMapping("/{postId}")
    public ResponseEntity<Void> deleteBoard(
        @PathVariable @Min(value = 1, message= "post Id는 최소 1을 입력하십시오.") long postId
    ) {
        postService.deletePost(postId);
        return ResponseEntity.noContent().build();
    }
   
}
