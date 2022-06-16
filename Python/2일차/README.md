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
    


```python

```
