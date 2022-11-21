# Vue

### 확장 프로그램
Vue VSCode Snippets
Vetur

## Componenet

1. 프로젝트 구성
   1. App.vue
      1. HelloWorld.vue
      2. count.vue
      3. calulAtrribute

### App.vue

```
<template>
  <img alt="Vue logo" src="./assets/logo.png">
  // 사용할 컴포넌트
  <HelloWorld msg="Welcome to Your Vue.js App"/> // props 전달
  <Count />
  <Calulate />
</template>

<script>
// 각 컴포넌트 import
import HelloWorld from './components/HelloWorld.vue'
import Count from './components/count.vue'
import Calulate from './components/calulAttribute.vue'
export default {
  name: 'App',
  // 위에서 import한 컴포넌트를 등록
  components: {
    HelloWorld,
    Count,
    Calulate
  },
}
</script>

<style>
#app {
  font-family: Avenir, Helvetica, Arial, sans-serif;
  -webkit-font-smoothing: antialiased;
  -moz-osx-font-smoothing: grayscale;
  text-align: center;
  color: #2c3e50;
  margin-top: 60px;
}
<style>
```



### HelloWorld.vue

```
<template>
  <div class="hello">
    <h1>{{ msg }}</h1>
  </div>
</template>

<script>
export default {
  name: 'HelloWorld',
  props: {
    msg: String
  }
}
</script>

<!-- Add "scoped" attribute to limit CSS to this component only -->
<style scoped>

</style>
```

> props로 전달되는 데이터의 타입을 정해 줘야된다.

### count.vue

```
<script>
export default {
  data() {
    return {
      firstName: 'John',
      lastName: 'Doe'
    }
  },
  computed: {
  // getter, setter 등록 가능
    fullName: {
      // getter
      get() {
        return this.firstName + ' ' + this.lastName
      },
      // setter
      set(newValue) {
        // Note: we are using destructuring assignment syntax here.
        [this.firstName, this.lastName] = newValue.split(' ')
      }
    }
  }
}

</script>
<template>
	<h2>
    {{this.fullName = 'jaehyun jeong'}} // setter 호출
  </h2>
  <h2>
    {{this.fullName}}
  </h2>

</template>
```



### 결과 화면

![image-20221122030708168](https://github.com/jaehyun0122/TIL/blob/master/VuePractice/asset/vue%EC%8B%A4%ED%96%89%ED%99%94%EB%A9%B4.png)

## 조건부 렌더링

### v-if

```
    <button @click="canSee = !canSee">Toggle</button>
    <h1 v-if="canSee">True</h1>
    <h1 v-else>False</h1>
    ...
    data(){
	    canSee: false;
    }
```

![image-20221122032507729](https://github.com/jaehyun0122/TIL/blob/master/VuePractice/asset/false.png)

![image-20221122032515818](https://github.com/jaehyun0122/TIL/blob/master/VuePractice/asset/true.png)

> 같은 목적으로 v-show도 존재
>
> v-show는 자주 토글해야 하는 경우, css의 display속성만 변경되기 때문에
>
> v-if가 토글 비용이 더 많이 든다. 컴포넌트가 실제로 제거되고 생성된다.

### 리스트 렌더링

### v-for

In 2.2.0+, when using v-for with a component, a key is now required.

v-for 사용시 key값을 지정해 줘야된다.

```
    <ul>
        <li v-for="(value, key) in myObject" v-bind:key="value">
            {{key}}: {{value}}
        </li>
    </ul>
    ...
    data(){
    	return{
        	myObject: {
                title: 'How to do lists in Vue',
                author: 'Jane Doe',
                publishedAt: '2016-04-10'
           }
    	}
    }
```

![image-20221122033732129](https://github.com/jaehyun0122/TIL/blob/master/VuePractice/asset/v-for.png)

## 라이프사이클

[Vue라이프사이클](https://vuejs.org/guide/essentials/lifecycle.html#lifecycle-diagram)
