## 함수형 컴포넌트
- 컴포넌트를 호출해 실행하면 내부 선언된 변수, 함수 등이 다시 선언되어 사용된다.
- props로 데이터를 전달할 경우 하위 컴포넌트들의 리렌더링이 일어나게 된다.

## useMemo
- 렌더링 최적화를 위해 사용
- 2개 이상의 데이터가 props로 하위 컴포넌트로 전달될 때 변경하지 않는 값도 실행된다.
=> 이런 문제를 해결할 수 있는 React Hook

### 1. useMemo 사용하지 않을 경우
color의 값만 변경해도 color, phone의 변경함수가 호출된다.
![](https://github.com/jaehyun0122/TIL/blob/master/react-hook/asset/beforeMemo.jpg)
### 2. useMemo 사용
변경 값만의 함수가 실행.
![](https://github.com/jaehyun0122/TIL/blob/master/react-hook/asset/afterMemo.jpg)

## useRef
- 형태
```
const refValue = useRef(initialValue)
```
- .current 프로퍼티에 변경 가능한 값을 담고있다.
- useState는 변경값을 실시간으로 보여지지만 useRef는 변경 상태 유지하고 특정 작업에 데이터 처리 할 떄 사용
예) form data 전송시
```
        ...
const refValue = useRef("");
        ...
        <>
            ...
            <input ref={refValue} type="text"></input>
            <button onClick={refChange}>전송</button>
            ...
        </>
```
![](https://github.com/jaehyun0122/TIL/blob/master/react-hook/asset/useRef.jpg)