<script>
    import { store } from '../store/store.js'

    export default {
        data(){
            return {
                store
            }
        }
    }
</script>

<template>
    <div>
        <h2>{{ store.name }}</h2>
    </div>
</template>

<style lang="scss" scoped>

</style>