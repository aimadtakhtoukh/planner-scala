<template>
  <section class="spinner">
    <font-awesome-icon icon="spinner" spin></font-awesome-icon>
  </section>
</template>

<script>
  import TokenService from "../services/TokenService";
  import CurrentUser from "../services/CurrentUser";

  export default {
    name: "Redirections",
    mounted() {
      if (!TokenService.getToken()) {
        this.$router.push("/not-logged")
      } else {
        CurrentUser.updateUser()
          .then(response => {
            if (!response) {
              this.$router.push("/subscribe")
            } else {
              this.$router.push("/main")
            }
          })
          .catch(error => {
            console.error(error);
          })
      }
    }
  }
</script>

<style scoped lang="scss">
  .spinner{
    text-align: center;

    svg {
      height: 50px;
      width: 50px;
    }
  }
</style>