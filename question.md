# 궁금한점

1. PostController - @PathVariable, @PathParam 차이
    PathVariable: /api/posts/1
    PathParam: /api/posts?postId=1

    PathParam이 더 지저분해 보이긴하는데.. 가독성 때문에 PathVariable을 쓴건지?
    
2. update 요청을 받을때 입력 DTO에 id값을 받지않고 PathVariable로 분리해 받은 이유는?

    뇌피셜로는 Post, Update시에 정보 노출을 최소화하는게 좋다고 생각해서
    id까지 body에 담아오려고 했는데...
    근데 url 보니까 get, post, delete, update 모두 일관되게 도메인/id/기능 이렇게 적은걸로 봐서
    규칙을 지키려는 의도가 있는것 같다..
    그리고 테이블의 id값은 노출돼도 딱히 활용할 수 있는게 없는것 같다.
    
# 내 코드랑 다른점
1. 유효성 검사를 Service가 아닌 Controller에서 @Valid, BindingResult로 하고있다.
    이렇게 하면 service계층까지 내려가지 않아도 controller에서 유효성 검사를 할 수 있다.
    장점은 service에서는 검증된 정보를 사용해서 비즈니스 로직 처리만 집중할 수 있다.
    그리고 만약 valid 조건이 변해도 DTO에 적힌 조건 한줄만 바꾸면 되기 떄문에 간편할듯
    service계층에서 유효성 검증을 했다면 유효성 검증을 따로 분리하지 않은 이상 하나씩 다 고쳐야되는 번거로움이 있다.
        
    
