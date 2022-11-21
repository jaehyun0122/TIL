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

![image-20221122030708168](C:\Users\jaehyun\AppData\Roaming\Typora\typora-user-images\image-20221122030708168.png)
