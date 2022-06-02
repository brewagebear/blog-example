# TCC 패턴 적용

자세한 글과 내용은 아래의 포스팅을 참고하시기 바랍니다.

[REST 기반의 간단한 분산 트랜잭션 구현](https://github.com/YooYoungmo/article-tcc)

1. [REST 기반의 간단한 분산 트랜잭션 구현 – 1편 TCC 개관](https://www.popit.kr/rest-%EA%B8%B0%EB%B0%98%EC%9D%98-%EA%B0%84%EB%8B%A8%ED%95%9C-%EB%B6%84%EC%82%B0-%ED%8A%B8%EB%9E%9C%EC%9E%AD%EC%85%98-%EA%B5%AC%ED%98%84-1%ED%8E%B8/)
2. [REST 기반의 간단한 분산 트랜잭션 구현 - 2편 TCC Cancel, Timeout](https://www.popit.kr/rest-%EA%B8%B0%EB%B0%98%EC%9D%98-%EA%B0%84%EB%8B%A8%ED%95%9C-%EB%B6%84%EC%82%B0-%ED%8A%B8%EB%9E%9C%EC%9E%AD%EC%85%98-%EA%B5%AC%ED%98%84-2%ED%8E%B8-tcc-cancel-timeout/)
3. [REST 기반의 간단한 분산 트랜잭션 구현 - 3편 TCC Confirm(Eventual Consistency)](https://www.popit.kr/rest-%EA%B8%B0%EB%B0%98%EC%9D%98-%EA%B0%84%EB%8B%A8%ED%95%9C-%EB%B6%84%EC%82%B0-%ED%8A%B8%EB%9E%9C%EC%9E%AD%EC%85%98-%EA%B5%AC%ED%98%84-3%ED%8E%B8-tcc-confirmeventual-consistency/)
4. [REST 기반의 간단한 분산 트랜잭션 구현 - 4편 REST Retry](https://www.popit.kr/rest-%EA%B8%B0%EB%B0%98%EC%9D%98-%EA%B0%84%EB%8B%A8%ED%95%9C-%EB%B6%84%EC%82%B0-%ED%8A%B8%EB%9E%9C%EC%9E%AD%EC%85%98-%EA%B5%AC%ED%98%84-4%ED%8E%B8-rest-retry/)

저는 유영모님의 코드를 기반해서 **order-service**와 **stock-service** 부분만 따와서 예제를 만들어보았습니다.
기존 코드에서는 상품 현재 재고보다 더 많은 재고 요청이 들어온 경우에 -로 재고 처리되는 부분이 존재하였는데 이를 추가 수정하였습니다.
+ 서비스 레이어에서 요청에 따른 현 재고를 검증하고 이에 따라 데드레터 발급 (3번 컨슘 트라이, 1초 백오프)

docker-compose를 통해서 1주키퍼, 1카프카 형태로 테스트할 수 있게 하였으며, sample 폴더 내에 http request로 테스트 수행을 하실 수 있습니다.
