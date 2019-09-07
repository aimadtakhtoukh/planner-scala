<template>
  <nav class="topnav">
    <a href="/">
      <img
        class="logo"
        src="/foresight-main.svg"
        alt="Calendar"/>
    </a>
    <div class="right-floating" v-show="connected">
      <span class="user-label">{{user.name || ""}}</span>
      <button class="btn disconnect-button" @click="disconnect">Déconnexion</button>
    </div>
  </nav>
</template>

<script>
  import TokenService from "../services/TokenService";
  import CurrentUser from "../services/CurrentUser";
  export default {
    name : 'navbar',
    computed : {
      user : () => CurrentUser.state.user,
      connected : () => CurrentUser.state.connected
    },
    methods : {
      disconnect() {
        TokenService.removeToken();
        CurrentUser.commit("removeUser");
        this.$router.push("/");
      },
      discordUrl : () => "/api/discord/login"
    },
    mounted() {
      CurrentUser.dispatch("updateUser")
    }
  }
</script>

<style lang="scss">
  .topnav {
    background-color: #333;
    overflow: hidden;
    display: flex;
    width: 100%;
    margin-bottom: 20px;

    * {
      background-color: #333;
    }

    span, a {
      align-self: flex-start;
      display: block;
      color: #f2f2f2;
      text-align: center;
      padding: 14px 16px;
      text-decoration: none;
      font-size: 17px;
    }

    img.logo {
      height: 70px;
      width: 210px;
    }

    .right-floating {
      align-self: center;
      margin-left: auto;
      height : 100%;
      margin-right : 10px;
      display: flex;
      flex-direction: column;

      .user-label {
        text-align: center;
        width: 100%;
      }

      .disconnect-button {
        background-color : #7289da;
        color : white;
        align-self: center;
      }

      .connect-button {
        background-color : #7289da;
        color : white;
        align-self: center;
      }
    }
  }
</style>