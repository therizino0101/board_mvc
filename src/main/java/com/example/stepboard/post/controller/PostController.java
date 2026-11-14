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

import com.example.stepboard.post.dto.PageDTO;
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

    // 페이지네이션 - 전체를 찾으면 시간이 존나게 오래걸리니까.. 쿼리 단계에서 페이지별로 쪼개서 가져오는것?
    // 결국에는 앞에 자를 수 OFFSET이랑 몇개를 보여줄지만 결정하면 된다.
    // 페이지 개념은... 몇개씩 잘라서 보여줄지 이니까 (보기 편하라고)
    // 만약에 10개씩 보여주는 페이지가 있다.
    // 첫번째 페이지를 보여주고싶다. 그러면
    // offset은 0이고, fetch next는 10 rows를 보여주면된다.

    // 두번째 페이지를 보여주고 싶다? 그러면
    // offset은 10, fetch next는 10을 보여준다.

    // 세번째 페이지를 보여주고 싶다? 그러면
    // offset은 20, fetch next는 10을 보여준다.

    // 공식은.. offset = (보여줄 개수) * (페이지 번호 - 1)

    @GetMapping("/{postId}")
    public ResponseEntity<Post> getPost(
        @PathVariable @Min(value = 1, message ="게시글 번호는 1 이상이어야 합니다")long postId
    ) {
        return ResponseEntity.ok(boardService.getPost(postId));
    }
    

    @GetMapping
    public ResponseEntity<List<Post>> getPosts (
        @RequestParam int pageNum,
        @RequestParam int pageCount
    ) {
        // 컨트롤러는 요청과 응답을 관리한다고 생각하기 때문에
        // 컨트롤러에서 Page 객체를 조립함.
        PageDTO pageDto = new PageDTO();
        int offset = pageCount * (pageNum -1 );
        pageDto.setOffset(offset);
        pageDto.setPageCount(pageCount);
        return ResponseEntity.ok(postService.getPosts(pageDto));
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
