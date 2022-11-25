# Http 웹 기본 지식

출처 : [모든 개발자를 위한 HTTP 웹 기본 지식](https://www.inflearn.com/course/http-%EC%9B%B9-%EB%84%A4%ED%8A%B8%EC%9B%8C%ED%81%AC/dashboard)

목표 : 개발자로서 꼭 필요한 HTTP 지식 습득

## 1. 인터넷 네트워크

### 인터넷 통신

1) IP

   지정한 IP 주소에 데이터 전달

   패킷 통신 단위

   **한계**

   - 비연결성
     - 패킷을 받을 대상이 없거나 서비스 불가능 상태에도 패킷 전송
   - 비신뢰성
     - 중간에 패킷 사라질 경우 => 복잡한 인터넷 망에서 중계 노드의 문제가 발생할 수 있다.
     - 패킷 순서 보장 X => MTU(Maximum Transmission Unit) 약 1500bytes => 패킷을 끊어 보낼 경우 순서 보장이 않될 수 있다.
   - 프로그램 구분
     - 같은 IP를 사용하는 서버에서 통신하는 애플리케이션이 둘 이상일 경우

2) TCP/UDP

   ![](https://github.com/jaehyun0122/TIL/blob/master/Http/asset/%EC%9D%B8%ED%84%B0%EB%84%B7%ED%94%84%EB%A1%9C%ED%86%A0%EC%BD%9C%EA%B3%84%EC%B8%B5.png) 인터넷프로토콜계층

   1. TCP

      1) TCP/IP 패킷 정보

         출발지IP, 목적지IP, 출발지PORT, 목적지PORT, 전송제어, 순서, 데이터,...

      2) 특징

         - 연결지향 - 3 way handshaking
         - 데이터 전달 보증 => 데이터 전송 후 수신 측에서 수신 메시지 전달
         - 순서 보장 => 수신측에서 패킷 순서가 잘 못되면 그 부분부터 재전송 요청
         - 신뢰/대부분 TCP 사용

![](https://github.com/jaehyun0122/TIL/blob/master/Http/asset/3wayhandshake.png) 3way

		2. UDP

     1. 특징

        IP + PORT + checksum

        단순하고 빠름

        연결지향 X, 순서 보장 X, 데이터 전달 보증 X

		3. PORT

     한번에 둘 이상 연결할 경우(게임, 영상 스트리밍,...)

     같은 IP 내에서 프로세스 구분(locahost:8080, localhost:3000 등)

     0~65535 할당 가능

     0~1023: Well Known port, 권장하지 않음

		4. DNS

     도메인 명을 IP 주소로 변환

![](https://github.com/jaehyun0122/TIL/blob/master/Http/asset/DNS.png) dns

