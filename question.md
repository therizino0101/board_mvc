# 궁금한점

1. PostController - @PathVariable, @PathParam 차이
    PathVariable: /api/posts/1
    PathParam: /api/posts?postId=1

    PathParam이 더 지저분해 보이긴하는데.. 가독성 때문에 PathVariable을 쓴건지?


    정답: 
        - 1. pathParam x, requetParam O <- spring framework
        - 2. PathVariable를 사용해서 동적 링크로 url 관리가 근본
    
2. update 요청을 받을때 입력 DTO에 id값을 받지않고 PathVariable로 분리해 받은 이유는?

    뇌피셜로는 Post, Update시에 정보 노출을 최소화하는게 좋다고 생각해서
    id까지 body에 담아오려고 했는데...
    근데 url 보니까 get, post, delete, update 모두 일관되게 도메인/id/기능 이렇게 적은걸로 봐서
    규칙을 지키려는 의도가 있는것 같다..
    그리고 테이블의 id값은 노출돼도 딱히 활용할 수 있는게 없는것 같다.

    정답:
        - url은 어디에 있는 자원을 수정할것인지 지정
        - body는 어떻게 수정할 것인지 (변경할 값만 넣는게 좋을 수 있음)
        - body에 id를 넣으면 충돌할 수 있음.
        - 보안
            - id값도 해싱적용해서 보낼 수 있음.
            - id값으로 보안 상태나 테이블 구조를 추측할 수 있음.




3. POST 요청 response에 생성된 객체값을 같이 보내는 이유는?
    회원가입후 생성된 정보를 화면에 보여주는 경우가 있기 때문인건가?

    - 클라이언트는 이걸 받아서 바로 쓰려고
    - POST는 생성된 자원을 바로 사용가능하게 함.
    201: 자원 생성됐다는 결과
    

4. POST 요청 시 Service 계층에서 repository에 저장한 뒤 다시 findPost로 찾아오는 이유는? 저장할때 사용했던 post 객체에 id 값이 들어갈텐데..? 혹시 delYn, 생성일시같은 DB에서 생성되는 값은 자동으로 채워지지 않기때문인가?
    

5. repository에서 save할때 DB에서 저장을 실패하는 경우가 있나? 앞서 Controller에서 예외처리를 제대로 했다면 저장하는데 문제는 없을것 같은데..
그리고 왜 IllegalStateException이지? 저장을 해야 정상 상태인데 저장하지 못해서 비정상상태라는거?


6. DB에 한번 요청을 보내는게 큰 비용이 드나?


# 내 코드랑 다른점
1. 유효성 검사를 Service가 아닌 Controller에서 @Valid, BindingResult로 하고있다.
    이렇게 하면 service계층까지 내려가지 않아도 controller에서 유효성 검사를 할 수 있다.
    장점은 service에서는 검증된 정보를 사용해서 비즈니스 로직 처리만 집중할 수 있다.
    그리고 만약 valid 조건이 변해도 DTO에 적힌 조건 한줄만 바꾸면 되기 떄문에 간편할듯
    service계층에서 유효성 검증을 했다면 유효성 검증을 따로 분리하지 않은 이상 하나씩 다 고쳐야되는 번거로움이 있다.
        
    
