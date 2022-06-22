# Spring Cloud

https://spring.io/projects/spring-cloud

### 서버를 여러 개 두어 서비스를 안정적으로 운영하기 위해

## 웹 서비스 확장 전략

- 실부 개발 유형
  - 솔루션개발(B2B) 
  - SI 개발(cloud가 필요하지 않을 수도 있다) 
  - 서비스 개발(고가용성, B2C)

## 스케일업을 통한 서비스 확장

서버 성능에 따른 비용 증가가 이뤄지므로 비용 및 서비스 업데이트 측면에서 어려움이 있다.

## 스케일아웃을 통한 서비스 확장

여러대의 서버들이 트래픽을 나눌 수 있으므로 장애대응에 효과적

### 블루그린 배포

- before(블루) | after(그린)
- 기존의 운영중인 서버 소스와 배포해 나갈 소스를 분리해 무중단 배포를 할 수 있다
- 이전의 서버를 하나 씩 내려 after 코드로 바꿔줌

## 서버 부하 분산을 위한 네트워크

- 서버 : 무언가를 제공하는 대상 또는 그 주체
- 부하 : Load
- 분산 : 갈라져 흩어짐
- 네트워크 : 통신설비를 갖춘 컴퓨터를 이용해 서로 **연결**시켜주는 조직이나 체계

**DMZ Zone** : 개발자에게 중요한 영역

## DNS 

https://www.cloudflare.com/ko-kr/learning/dns/what-is-dns/

외부, 내부 => DMZ Zone(DNS & Host)

### 실습

C:\Windows\System32\drivers\etc의 hosts파일

=>127.0.0.1 dns.test.com 추가

```
package com.example.dnsapp;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;

@SpringBootApplication
public class DnsAppApplication {

    @RestController
    class HelloController{
        @GetMapping("/")
        public String Hello(){
            return "Hello DNS";
        }
    }
    public static void main(String[] args) {
        SpringApplication.run(DnsAppApplication.class, args);
    }

}
```



![before](https://github.com/jaehyun0122/TIL/blob/master/Spring/Cloud/asset/before.png)

![after](https://github.com/jaehyun0122/TIL/blob/master/Spring/Cloud/asset/after.png)

### DNS 매핑 시 웹서버를 이용한 API 서버 연동

WellKnow port와 권한 IANA

=> WellKnown port를 리눅스에서 사용할 시 루트 권한이 있어야한다. 

1. 권한 있는 유저로 실행 시 => 보안 이슈가 발생할 수 있다. (DB서버 접근, 서버 다운 등)
2. client => web server => spring boot

​                  80                       8080

​		web server와 api 서버의 권한이 다르므로 api서버에 루트 권한을 주지 않아도 80포트 이용가능.

HTTPS : 설정 시 Wild카드 인증서 구매해야 함

- a.b.c.com => com앞에 .이 3개인 경우 Https 지원을 할 수 없다
- a-b.c.com => 가능

## 읽기 요청 부하 분산

최초 요청 : Internet => api server => DB server

2번 째 동일한 요청 : Internet => api server / **cache** => DB server

=> 같은 요청에 대해 DB에 요청하지 않으므로 부하를 줄일 수 있다.



internet => api server => cache server => DB

​					1, 2, 3, ....

=> api server 각각에 cache가 있어 데이터 정합성을 유지할 수 없다.

=> cache server를 따로 둠

## 클러스터링 & 레플리카

DB 부하 분산 / 캐시로 감당할 수 없는 경우

### 클러스터링

HA(High Available) : DB 서버를 여러개

api server

---

db server    db server

---

db server   db server

db server   db server



### 레플리카

api server

db server(읽기만) db server(일기, 쓰기) db server

## 샤딩

https://engineering.linecorp.com/ko/blog/line-manga-database

데이터 기준으로 나눔. 같은 테이블 스키마를 가진 데이터를 다수의 데이터베이스에 분산 저장.

샤딩키

| pNum | date     |
| ---- | -------- |
| 123e | 20220621 |

​													=>  DB  DB  DB 샤딩키 기준으로 분산 저장
## 쓰기 요청 분산

동영상 업로드                                                                 

​										=> api server

이미지, 게시글 조회

---

동영상 업로드				=> upload server		

​										=> api server

이미지, 게시글 조회



- 폴링
- callback event

### EventListener 

- ### spring Event 사용

```
package com.example.eventapp.storage;

import com.example.eventapp.event.FileEvent;
import com.example.eventapp.event.FileEventPublisher;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.Map;

@Slf4j
@Service
public class FileService {
    @Autowired
    private FileEventPublisher fileEventPublisher;

    public void fileUpload(Map<String, Object> data){
        try{
          log.info("파일 복사 완료");
          log.info("DB 저장 완료");

            FileEvent completeEvent = FileEvent.completeEvent(data);
            fileEventPublisher.notifyComplete(completeEvent);
        } catch (Exception e){
            log.error("file upload fail", e);
            FileEvent errorEvent = FileEvent.errorEvent(data);
            fileEventPublisher.notifyError(errorEvent);
        }
    }

}
```

=> service에서 데이터를 받아 이벤트를 날려준다

```
package com.example.eventapp.event;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.ApplicationEventPublisher;
import org.springframework.stereotype.Component;

@Component
public class FileEventPublisher {
    @Autowired
    private ApplicationEventPublisher applicationEventPublisher;

    public void notifyComplete(FileEvent fileEvent){
        applicationEventPublisher.publishEvent(fileEvent);
    }

    public void notifyError(FileEvent fileEvent){
        applicationEventPublisher.publishEvent(fileEvent);
    }
}
```

=> ApplicationEventPublisher를 사용하면 이벤트 감지 가능

```
package com.example.eventapp.event;

import lombok.extern.slf4j.Slf4j;
import org.springframework.context.event.EventListener;
import org.springframework.stereotype.Component;

@Slf4j
@Component
public class FileEventListenr {
    @EventListener
    public void onFileEventHandler(FileEvent fileEvent){
        log.info("file event receice type:{} data:{}",fileEvent.getType(), fileEvent.getData());
        
        if(fileEvent.getType().equals("COMPLETE")){
        	...
        }
    }
}

```

=> 조건문을 사용해 여러가지 기능(메시지 전송, ...)을 제공할 수 있다.

```
2022-06-22 13:25:35.428  INFO 15576 --- [nio-8080-exec-1] c.example.eventapp.storage.FileService   : 파일 복사 완료
2022-06-22 13:25:35.429  INFO 15576 --- [nio-8080-exec-1] c.example.eventapp.storage.FileService   : DB 저장 완료
2022-06-22 13:25:35.432  INFO 15576 --- [nio-8080-exec-1] c.e.eventapp.event.FileEventListenr      : file event receice type:COMPLETE data:{fileSize=10, id=jeong, type=web}
```

- ### 메시지 큐 사용

  #### 분산 환경에서 메시지를 효율적으로 처리하기 위해 사용

  
