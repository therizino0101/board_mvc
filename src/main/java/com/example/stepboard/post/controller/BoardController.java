package com.example.stepboard.post.controller;

import java.net.URI;
import java.util.HashMap;
import java.util.Map;

import org.springframework.http.ResponseEntity;
import org.springframework.validation.BindingResult;
import org.springframework.validation.FieldError;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
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

import jakarta.validation.Valid;
import jakarta.validation.constraints.Min;
import lombok.RequiredArgsConstructor;

@RestController
// @RequestMapping("/board")
// NOTE: restAPI는 url에 /api로 시작되도록 하는게 좋음.
@RequestMapping("/api/posts")
@RequiredArgsConstructor
@Validated 
public class BoardController {
    
    private final BoardService boardService;

    @PostMapping
    public ResponseEntity<Post> createBoard(@Valid @RequestBody PostRequest request) {
       
        Post post = boardService.createPost(request);
        return ResponseEntity.created(URI.create("/api/posts/" + post.getPostId()))
            .body(post);
    }

    // TODO2: 게시판 상세조회
    // @GetMapping
    // // Spring framework를 사용하는게 적절함
    // public ResponseDTO  searchDetail(@RequestParam String id) {
    // //public ResponseDTO searchDetail(@PathParam(value = "id") String id) {
    //     // 왜 dto가 아닌 id를 썼는지는 모르겠음.
    //     return boardService.searchDetail(id);
    // }

    @GetMapping("/{postId}")
    public ResponseEntity<Post> getPost(
        // 왜 에러 throw가 안되고 그냥 그대로 return 하는걸까?
        // @Valid를 안써서?
        // Throw를 하려면 뭔가,.. Class에 Valid를 추가해야되나>
        @PathVariable @Min(value = 1, message ="게시글 번호는 1 이상이어야 합니다")long postId
    ) {
        System.out.println("받아온 postId:" + postId);
        return ResponseEntity.ok(boardService.getPost(postId));
    }
    
    
    // TODO3: 게시판 목록조회
    @GetMapping("/list")
    public ResponseDTO  searchList() {
        return boardService.searchList();
    }

    // TODO4: 게시판 수정
    @PostMapping("/update")
    public ResponseDTO  updateBoard(@RequestBody BoardDTO boardDTO) {
        return boardService.updateBoard(boardDTO);
    }

    // TODO5: 게시판 삭제
    @DeleteMapping("/delete")
    public ResponseDTO  deleteBoard(@RequestBody BoardDTO boardDTO) {
        // 삭제 성공하면 204
        return boardService.deleteBoard(boardDTO);
    }
   
}
