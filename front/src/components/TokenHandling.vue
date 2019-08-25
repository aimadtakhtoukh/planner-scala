<template>
    <font-awesome-icon icon="spinner" spin></font-awesome-icon>
</template>

<script>
    import TokenService from "../services/TokenService"
    export default {
        name: "TokenHandling",
        mounted() {
            const accessToken = this.$route.query.access_token.toString();
            TokenService.saveToken(accessToken);
            this.$router.push("/")
        }
    }
</script>

<style scoped>

</style>