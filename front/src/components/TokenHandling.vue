<template>
    <div>
        Token!
    </div>
</template>

<script>
    export default {
        name: "TokenHandling",
        mounted() {
            console.info(this.$route.query.code)
        }
    }
</script>

<style scoped>

</style>