<template>
  <img alt="Vue logo" src="./assets/logo.png">
  <HelloWorld msg="Welcome to Your Vue.js App"/>
  <Count />
  <Calulate />
</template>

<script>
import HelloWorld from './components/HelloWorld.vue'
import Count from './components/count.vue'
import Calulate from './components/calulAttribute.vue'
export default {
  name: 'App',
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
</style>
