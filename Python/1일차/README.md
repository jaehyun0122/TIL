## 아나콘다 = python + 라이브러리 + IDE

## 크롤링 기법 소개
Requests + BeautifulSoup

Selenium + BeautifulSoup

Only Selenium

### Requests
### Selenium
### BeautifulSoup ( 정보 찾는 용도 )

### Request ( HTML 내부 데이터만 )

#### 특징 

굉장히 빠르다 => 웹페이지내 데이터만 가져오기 때문에 외부에 데이터가 존재할 시 데이터를 가져올 수 없다.

### Selenium ( HTML 외부 데이터도 가능 )

#### 특징

브라우저 조작 가능

모든 데이터 가져올 수 있다

어느 페이지를 크롤링 중인지 눈으로 확인가능 => Requests에 비해 상대적으로 느리다


### BeautifulSoup

#### 특징

정보찾기에 사용

빠르다


## 명령어 비교
### BeautifulSoup select('조건A')

### Selenium      find_elements_by_css_selector('조건A')

=> 두 함수 동일한 형태로 입력 가능 => 학습/활용에 용이

## 크롤링 기법 선택

url에 접속 => 

if(HTML 내에 원하는 정보 존재)

    Requests 활용
    
else if 외부 데이터 url 확인 가능

    if(크롤링 가능)
    
        Requests 활용
        
    else Selenium 활용
    
else Selenium 활용


```python

```
