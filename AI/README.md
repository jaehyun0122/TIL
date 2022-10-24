## 데이터 분석 라이브러리
### Python Module & Package
1. Module
- 특정 목적을 가진 함수, 자료의 모음
- import ___ : import 뒤에 키워드를 사용
2. Package
- 모듈을 디렉터리로 구분해 관리하는 것
- 모듈 관리가 편리해짐
- import A.B : A 폴더의 B 모듈 import 
- from A import B : A 모듈의 B 함수를 import
```
from urllib.request import urlopen 

page = urlopen("https://www.naver.com/").read().decode("utf-8")

print(page)
```

### Numpy
Numerical Python
Python에서 다차원 배열을 다룰 수 있는 라이브러리

데이터의 대부분을 숫자 배열로 볼 수 있기 때문에 Numpy사용
이미지, 소리 등을 배열로 나타일 수 있다.
반복문 없이 배열처리 가능 => 리스트 보다 빠른 연산, 효율적
```
list_arr = list(range(5))
print(list_arr)
print(type(list_arr))
```
[0, 1, 2, 3, 4]
<class 'list'>
```
import numpy as np

np_arr = np.array(range(5))
print(np_arr)
print(type(np_arr))
```
[0 1 2 3 4]
<class 'numpy.ndarray'>

- 리스트와 달리 같은 데이터 타입만 저장 가능
- ndim : 몇 차원 배열인지 확인가능
```
list = [0,1,2,3]
np_arr = np.array(list)
print(np_arr.shape)
print(np_arr.ndim)
print(np_arr.dtype)
print("<<<>>>")
list = [
    [1,2,3],
    [4,5,6]
]
np_arr = np.array(list)
print(np_arr.shape)
print(np_arr.ndim)
```
(4,)
1
int64
<<<>>>
(2, 3)
2
- shape으로 행열크기 지정
```
matrix = np.array(range(1,17))
matrix.shape = 4,4

print(matrix)
print(matrix.shape)
print(matrix.ndim)
print("<<<>>>>")
matrix.astype('str')
# 행은 0~2, 열은 1~2 까지만 출력
print(matrix[0:3, 1:3])
```
[[ 1  2  3  4]
 [ 5  6  7  8]
 [ 9 10 11 12]
 [13 14 15 16]]
(4, 4)
2
<<<>>>>
[[ 2  3]
 [ 6  7]
 [10 11]]

 - Indexing & Slicing
 np_arr[a : b : c] : a~b-1까지 c의 간격으로
 - Boolean Indexing
 ```
 arr = np.arange(8)
print(arr > 3)
print(arr[arr > 3])

 ```
[False False False False  True  True  True  True]

[4 5 6 7]
- Fancy Indexing
```
print(arr[[1,4,6]]) => 1,4,6 인덱스의 데이터
arr = np.arange(1, 17).reshape(4,4)
print(arr[[0,2], 2]) => 0,2 행의 2열 데이터
```
[1 4 6]

[ 3 11]