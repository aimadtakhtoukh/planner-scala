<template>
    <font-awesome-icon icon="spinner" spin></font-awesome-icon>
</template>

<script>
    import TokenService from "../services/TokenService"
    export default {
        name: "TokenHandling",
        mounted() {
            const code = this.$route.query.code.toString();
            TokenService.saveToken(code);
            this.$router.push("/")
        }
    }
</script>

<style scoped>

</style>