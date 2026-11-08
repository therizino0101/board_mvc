package com.example.board.controller;

import java.net.URI;
import java.util.HashMap;
import java.util.Map;

import org.springframework.http.ResponseEntity;
import org.springframework.validation.BindingResult;
import org.springframework.validation.FieldError;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.util.UriComponentsBuilder;

import com.example.board.dto.BoardDTO;
import com.example.board.dto.ResponseDTO;
import com.example.board.model.Board;
import com.example.board.model.Post;
import com.example.board.model.PostRequest;
import com.example.board.service.BoardService;

import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;

@RestController
// @RequestMapping("/board")
// NOTE: restAPI는 url에 /api로 시작되도록 하는게 좋음.
@RequestMapping("/api/posts")
@RequiredArgsConstructor
public class BoardController {
    
    private final BoardService boardService;

    // TODO1: 게시판 생성
    @PostMapping
    // NOTE: return할때 상태코드를 반환하도록 한다.
    // NOTE: <?> 타입을 사용하면 error map 객체, DTO 객체 모두 response에 실을 수 있다.
    public ResponseEntity<Post> createBoard(@Valid @RequestBody PostRequest request) {
        // @Valid에서 예외 발생 시 message를 출력하기 위함.
        // if(bindingResult.hasErrors()) {
        //     Map<String, String> errors = new HashMap<>();
            
        //     for(FieldError error: bindingResult.getFieldErrors()) {
        //         errors.put(error.getField(), error.getDefaultMessage());
        //     }
        //     return ResponseEntity.badRequest().body(errors);
        // }
        // 컨트롤러는 요청을 받아서 올바른 비즈니스 로직에 연결만 해주면 되니까 바로 service 호출
        // 생성 성공 시 201, 생성된 URL 주소를 반환
        // return boardService.createBoard(boardParam);
        Post post = boardService.createBoard(request);
        return ResponseEntity.created(URI.create("/api/posts/" + post.getPostId()))
            .body(post);
    }

    // TODO2: 게시판 상세조회
    @GetMapping
    // Spring framework를 사용하는게 적절함
    public ResponseDTO  searchDetail(@RequestParam String id) {
    //public ResponseDTO searchDetail(@PathParam(value = "id") String id) {
        // 왜 dto가 아닌 id를 썼는지는 모르겠음.
        return boardService.searchDetail(id);
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
