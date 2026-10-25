package com.example.board.service;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.util.List;

import org.apache.ibatis.session.SqlSession;
import org.springframework.stereotype.Service;

import com.example.board.common.error.ApiException;
import com.example.board.common.error.ExceptionEnum;
import com.example.board.dto.BoardDTO;
import com.example.board.dto.ResponseDTO;
import com.example.board.model.Board;

import io.micrometer.common.util.StringUtils;
import lombok.RequiredArgsConstructor;

// Spring에 컴포넌트 등록을 위해서 추가
@Service
// 생성자 코드 쓰면 지저분해져서 추가.
// 스프링이 실행되면 빈을 주입해줌.
@RequiredArgsConstructor 
public class BoardService {
    // mybatis로 sql을 호출하기 위해서 의존성 추가
    private final SqlSession sqlSession;
    final String NAMESPACE = "board.";
    
      /*
        description: 게시글 생성
        input: 
            - title
            - content
        output:
            - ResponseDTO
    */
    public ResponseDTO createBoard(BoardDTO boardParam) {
        // 결과 정보를 담을 객체
        ResponseDTO responseDTO = new ResponseDTO();
        // NOTE: insert할때 param을 안넣으면 값이 안들어간다..
        // 유효성 검증
        if(StringUtils.isBlank(boardParam.getTitle()) || StringUtils.isEmpty(boardParam.getContent())) {
            // 유효성 검증에 실패하면 FAIL 리턴하고, 에러 내용을 사용자에게 알려주기 위해서 사용
            throw new ApiException(ExceptionEnum.FAIL, "글 제목 또는 본문을 입력하세요");
            
        }
        // DTO로 받아온 정보를 모델 객체에 담아서 테이블에 필요한 정보를 모두 저장
        Board board = new Board();
        board.setTitle(boardParam.getTitle());
        board.setContent(boardParam.getContent());
        board.setRegDt(LocalDateTime.now());
        board.setUpdDt(LocalDateTime.now());
        // DB에 저장하고 저장된 행 수를 반환
        int createResult = sqlSession.insert(NAMESPACE + "insertBoard", board);
        // DB에 저장된 정보가 있다면 성공 반환
        if(createResult > 0) {
            responseDTO.setResult(ExceptionEnum.SUCCESS);
            responseDTO.setMessage("저장 성공");
            return responseDTO;
        } else {
            throw new ApiException(ExceptionEnum.FAIL, "저장 실패");
        }
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
    public ResponseDTO searchList() {
        ResponseDTO responseDTO = new ResponseDTO();
        
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
    public ResponseDTO updateBoard(BoardDTO boardDTO) {
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
        } else{
            throw new ApiException(ExceptionEnum.FAIL, "존재하지 않거나 삭제된 게시글입니다.");
            // responseDTO.setResult(ExceptionEnum.FAIL);
            // responseDTO.setMessage("삭제 실패");
            // return responseDTO;
        }
    }
}

