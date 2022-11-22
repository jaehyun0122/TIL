<script>
export default {
  name: 'Count',
  data() {
    return {
      firstName: 'John',
      lastName: 'Doe',
      canSee: false,
      myObject: {
        title: 'How to do lists in Vue',
        author: 'Jane Doe',
        publishedAt: '2016-04-10'
      }
    }
  },
  computed: {
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
        {{this.fullName = 'jaehyun jeong'}}
    </h2>
    <h2>
        {{this.fullName}}
    </h2>

    <button @click="canSee = !canSee">Toggle</button>
    <h1 v-if="canSee">True</h1>
    <h1 v-else>False</h1>

    <h1>v-for</h1>
    <ul>
        <li v-for="(value, key) in myObject" v-bind:key="value">
            {{key}}: {{value}}
        </li>
    </ul>


</template>