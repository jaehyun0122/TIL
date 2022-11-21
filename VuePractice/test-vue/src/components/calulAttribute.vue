<template>
    <div>
        <h2>{{ calulateMessage() }}</h2>    
        <h2>{{ computedCalulateMessage }}</h2>    
    </div>
</template>

<script>
    export default {
        name: 'Calulate',
        data() {
            return {
                author:{
                    name: 'john',
                    books:[
                        'v1 - basic',
                        'v2 - mid',
                        'v3 - advance'
                    ]
                }
            }
        },
        methods: {
            calulateMessage(){
                return this.author.books.length > 0 ? 'over 0' : 'underEqual 0';
            }
        },
        computed:{
            computedCalulateMessage(){
                return this.author.books.length > 0 ? 'over 0' : 'underEqual 0';
            }
        }
    }
</script>

<style lang="scss" scoped>

</style>