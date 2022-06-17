# 숫자 데이터


```python
x = 15
print(x)
```

    15
    


```python
type(x)
```




    int



# 문자 데이터


```python
name = "jaehyun"
print(name)
```

    jaehyun
    


```python
type(name)
```




    str



# 리스트


```python
list = [1,2,3,4,5]
print(list)
list[2]
```

    [1, 2, 3, 4, 5]
    




    3




```python
list[-1]
```




    5




```python
list[1:3]
```




    [2, 3]




```python
len(list)
```




    5




```python
list[::-1]
```




    [5, 4, 3, 2, 1]



## 리스트 병합


```python
list1 = ["사과", "배", "딸기"]
list2 = ["바나나", "블루베리", "파인애플"]

print(list1+list2)
```

    ['사과', '배', '딸기', '바나나', '블루베리', '파인애플']
    


```python
### 원소 추가
list1.append(["a","b","c"])
print(list1)
```

    ['사과', '배', '딸기', '감자', ['a', 'b', 'c']]
    


```python
print(list1[4][0])
```

    a
    

## for 반복문


```python
list = [1,2,3,4,5]
for i in list:
    print(i)
```

    1
    2
    3
    4
    5
    

## if문

if 조건1:
    조건 1 참일 경우 실해문
elif 조건2:
else:


```python
n = 10
if n>6:
    print("large than 6")
```

    large than 6
    


```python
list = ["배","사과"]
if "배" in list:
    print("Yes")
```

    Yes
    

## 문자열


```python
msg = "hell world"
print(msg)
```

    hell world
    


```python
name = "jaehyun"
msg = "hello {}".format(name)
print(msg)
```

    hello jaehyun
    


```python
name = "jaehyun"
msg = "hello {}  {}".format(name, "welcome")
print(msg)
```

    hello jaehyun  welcome
    

### f-string


```python
name = "jaehyun"
string = "welcome"
msg = f"hello {name}  {string}"
print(msg)
```

    hello jaehyun  welcome
    


```python
nameList = ["scot","james","rooney"]
for name in nameList:
    msg = f"hello {name} welcome"
    print(msg)
```

    hello scot welcome
    hello james welcome
    hello rooney welcome
    

### strip() : 앞뒤 공백 제거


```python
string = "\n\n\n 가나다라마바사아자차카타파하"
print(string)
```

    
    
    
     가나다라마바사아자차카타파하
    


```python
print(string.strip())
```

    가나다라마바사아자차카타파하
    

### replace()


```python
string.strip().replace("가나","가 나")
```




    '가 나다라마바사아자차카타파하'



### split()


```python
country = "a,b,c,d,e"
country.split(",")
```




    ['a', 'b', 'c', 'd', 'e']




```python

```
