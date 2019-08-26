<template>
  <section class="subscribe">
    <h3>Tu n'es pas encore inscrit ! Dernier détail, ajoute un pseudo.</h3>
    <input class="name-input" type="text" placeholder="Nom" v-model="name"/>
    <button class="btn" @click="subscribe()">Se connecter</button>
  </section>
</template>

<script>
  import UserService from "../services/UserService";

  export default {
    name: "Subscribe",
    data() {
      return {
        name : ""
      }
    },
    methods : {
      subscribe() {
        UserService.createUser({name : this.name}).then(() => this.$router.push("/"))
      }
    }
  }
</script>

<style lang="scss" scoped>
  .subscribe {
    text-align: center;

    .attention-message {
      width : 100%;
      text-align: center;
      padding-bottom: 2rem;
    }

    .name-input {
      background-color: #36393f;
      color: white;
      border-radius: 3px;
      border: 1px solid #606060;
      height: 3.3rem;
    }

    .btn {
      background-color : #7289da;
      color : white;
      font-size: 1.3rem;
      margin: 20px;
      height: 3.3rem;
    }

  }
</style>