package com.example.stepboard.post.service;

import java.time.LocalDateTime;
import java.util.List;
import java.util.Objects;

import org.apache.ibatis.session.SqlSession;
import org.jspecify.annotations.Nullable;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.example.stepboard.post.common.error.ApiException;
import com.example.stepboard.post.common.error.ExceptionEnum;
import com.example.stepboard.post.common.exception.PostNotFoundException;
import com.example.stepboard.post.dto.BoardDTO;
import com.example.stepboard.post.dto.ResponseDTO;
import com.example.stepboard.post.model.Board;
import com.example.stepboard.post.model.Post;
import com.example.stepboard.post.model.PostRequest;
import com.example.stepboard.post.repository.PostRepository;

import io.micrometer.common.util.StringUtils;
import lombok.RequiredArgsConstructor;

// Spring에 컴포넌트 등록을 위해서 추가
@Service
// 생성자 코드 쓰면 지저분해져서 추가.
// 스프링이 실행되면 빈을 주입해줌.
@RequiredArgsConstructor 
public class BoardService {
    private final PostRepository postRepository;
    // mybatis로 sql을 호출하기 위해서 의존성 추가
    private final SqlSession sqlSession;
    final String NAMESPACE = "post.";
    
      /*
        description: 게시글 생성
        input: 
            - title
            - content
        output:
            - ResponseDTO
    */
    @Transactional 
    public Post createPost(PostRequest request) {
        Post post = new Post();
        post.setTitle(request.getTitle().strip());
        post.setContent(request.getContent().strip());
        
        long postId = postRepository.save(post);
        
        return findById(postId);
    }

    /*
        description: 게시판 상세조회
        input: id
        output: ResponseDTO
    */
    public ResponseDTO searchDetail(String id) {
        
        ResponseDTO responseDTO = new ResponseDTO();
        if(StringUtils.isBlank(id)) {
            throw new ApiException(ExceptionEnum.FAIL, "게시판의 id를 입력하세요");
            // responseDTO.setResult(ExceptionEnum.FAIL);
            // responseDTO.setMessage("게시판의 id를 입력하세요");
            // return responseDTO;
        }
        // 조회할 id값이 테이블에 없는경우 알려주기 위해서 추가.
        int isExist = sqlSession.selectOne(NAMESPACE + "countBoardById", id);
        if(isExist < 1) {
            throw new ApiException(ExceptionEnum.FAIL, "존재하지 않거나 삭제된 게시글입니다.");
            // responseDTO.setResult(ExceptionEnum.FAIL);
            // responseDTO.setMessage("존재하지 않거나 삭제된 게시글입니다.");
            // return responseDTO;
        }
        BoardDTO board = sqlSession.selectOne(NAMESPACE + "findBoardInfoById", id);
        responseDTO.setResult(ExceptionEnum.SUCCESS);
        responseDTO.setMessage("조회 성공");
        responseDTO.setContent(board);
        return responseDTO;
        
    }

    /*
        description: 게시글 목록 조회
        input: N/A
        output: ResponseDTO
    */
    // 목록 전체를 불러오면 성능문제 발생 가능, pagination 구현해보기.
    public ResponseDTO  searchList() {
        ResponseDTO responseDTO = new ResponseDTO();
        // 서비스에서 repository까지 책임돼서 역할이 과도하게 됨.
        List<BoardDTO> boardList = sqlSession.selectList(NAMESPACE + "findAll");

        responseDTO.setResult(ExceptionEnum.SUCCESS);
        responseDTO.setMessage("목록 조회에 성공했습니다.");
        responseDTO.setContent(boardList);
        return responseDTO;
    }

    /*
        description: 게시글 수정
        input: 
            - id
            - title
            - content
        output: ResponseDTO
    */
   // upd_dt 갱신 코드가 없음
    public ResponseDTO  updateBoard(BoardDTO boardDTO) {
        ResponseDTO responseDTO = new ResponseDTO();
        if(StringUtils.isBlank(boardDTO.getId())) {
            throw new ApiException(ExceptionEnum.FAIL, "존재하지 않거나 삭제된 게시글입니다.");
            // responseDTO.setResult(ExceptionEnum.FAIL);
            // responseDTO.setMessage("ID를 입력하세요");
            // return responseDTO;
        }

        if(StringUtils.isBlank(boardDTO.getTitle()) || StringUtils.isBlank(boardDTO.getContent())) {
            throw new ApiException(ExceptionEnum.FAIL, "존재하지 않거나 삭제된 게시글입니다.");
            // responseDTO.setResult(ExceptionEnum.FAIL);
            // responseDTO.setMessage("제목 또는 본문을 입력하세요.");
            // return responseDTO;
        }

        int isExist = sqlSession.selectOne(NAMESPACE + "countBoardById", boardDTO);
        if(isExist < 1) {
            throw new ApiException(ExceptionEnum.FAIL, "존재하지 않거나 삭제된 게시글입니다.");
            // responseDTO.setResult(ExceptionEnum.FAIL);
            // responseDTO.setMessage("존재하지 않거나 삭제된 게시글입니다.");
            // return responseDTO;
        }
        // NOTE: update할때 파라미터를 넣어줘야 쿼리에 값이 들어가고, update가 실행된다.
        int resultCount = sqlSession.update(NAMESPACE + "updateBoard", boardDTO);
        if(resultCount >0) {
            responseDTO.setResult(ExceptionEnum.SUCCESS);
            responseDTO.setMessage("업데이트 완료");
            return responseDTO;
        } else {
            throw new ApiException(ExceptionEnum.FAIL, "존재하지 않거나 삭제된 게시글입니다.");
            // responseDTO.setResult(ExceptionEnum.FAIL);
            // responseDTO.setMessage("업데이트 실패");
            // return responseDTO;
        }
    }

    /*
        description: 게시글 삭제
        input: 
            - id
        output: ResponseDTO
    */
    public ResponseDTO deleteBoard(BoardDTO boardDTO) {
        ResponseDTO responseDTO = new ResponseDTO();
        if(StringUtils.isBlank(boardDTO.getId())) {
            throw new ApiException(ExceptionEnum.FAIL, "ID를 입력하세요");
            // responseDTO.setResult(ExceptionEnum.FAIL);
            // responseDTO.setMessage("ID를 입력하세요");
            // return responseDTO;
        }

        int isExist = sqlSession.selectOne(NAMESPACE + "countBoardById", boardDTO);

        if(isExist < 1) {
            throw new ApiException(ExceptionEnum.FAIL, "존재하지 않거나 삭제된 게시글입니다.");
            // responseDTO.setResult(ExceptionEnum.FAIL);
            // responseDTO.setMessage("존재하지 않는 게시글입니다.");
            // return responseDTO;
        }
        // NOTE: delete할때도 param이 안들어가면, 쿼리에서 #{} 이부분이 null로 들어간다.. 그래서 반영이 안됨.
        int resultCount = sqlSession.delete(NAMESPACE + "deleteBoard", boardDTO);
        if(resultCount > 0) {
            responseDTO.setResult(ExceptionEnum.SUCCESS);
            responseDTO.setMessage("삭제 완료");
            return responseDTO;
        } else {
            throw new ApiException(ExceptionEnum.FAIL, "존재하지 않거나 삭제된 게시글입니다.");
            // responseDTO.setResult(ExceptionEnum.FAIL);
            // responseDTO.setMessage("삭제 실패");
            // return responseDTO;
        }
    }

    private Post findById(long id) {
        Post post = postRepository.findById(id)
        .orElseThrow(() -> new PostNotFoundException(id));
    
        return post;
    }

    @Transactional(readOnly = true)
    public Post getPost(long postId) {
        return findPost(postId);
    }

    private Post findPost(long postId) {
        return postRepository.findById(postId)
        .orElseThrow(() -> new PostNotFoundException(postId));
    }
}