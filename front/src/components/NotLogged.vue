<template>
  <section class="not-logged">
    <h1 class="attention-message">Tu n'es pas connecté !</h1>
    <a href="/api/discord/login" class="btn login-button">Via Discord</a>
  </section>
</template>

<script>
  export default {
    name: "NotLogged"
  }
</script>

<style lang="scss" scoped>
  .not-logged {
    text-align: center;

    .attention-message {
      width : 100%;
      text-align: center;
      padding-bottom: 2rem;
    }

    .login-button {
      background-color : #7289da;
      color : white;
      font-size: 1.5rem;
    }
  }
</style>