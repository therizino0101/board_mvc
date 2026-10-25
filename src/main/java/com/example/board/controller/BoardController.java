package com.example.board.controller;

import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.example.board.dto.BoardDTO;
import com.example.board.dto.ResponseDTO;
import com.example.board.service.BoardService;

import jakarta.websocket.server.PathParam;
import lombok.RequiredArgsConstructor;

@RestController
@RequestMapping("/board")
@RequiredArgsConstructor
public class BoardController {
    // TODO: 공통 에러처리
    
    private final BoardService boardService;

    // TODO1: 게시판 생성
    @PostMapping("/create")
    public ResponseDTO createBoard(@RequestBody BoardDTO boardParam) {
        return boardService.createBoard(boardParam);
    }

    // TODO2: 게시판 상세조회
    @GetMapping("/detail")
    public ResponseDTO searchDetail(@PathParam(value = "id") String id) {
        return boardService.searchDetail(id);
    }
    
    // TODO3: 게시판 목록조회
    @GetMapping("/list")
    public ResponseDTO searchList() {
        return boardService.searchList();
    }

    // TODO4: 게시판 수정
    @PostMapping("/update")
    public ResponseDTO updateBoard(@RequestBody BoardDTO boardDTO) {
        return boardService.updateBoard(boardDTO);
    }

    // TODO5: 게시판 삭제
    @DeleteMapping("/delete")
    public ResponseDTO deleteBoard(@RequestBody BoardDTO boardDTO) {
        return boardService.deleteBoard(boardDTO);
    }
   
}
