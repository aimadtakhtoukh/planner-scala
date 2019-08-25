<template>
  <nav class="topnav">
    <a href="/">
      <img
        class="logo"
        src="/foresight-main.svg"
        alt="Calendar"/>
    </a>
    <div class="right-floating" v-if="connected">
      <span>{{user.name}}</span>
      <span>{{user}}</span>
      <button class="btn disconnect-button" v-on:click="disconnect">Déconnexion</button>
    </div>
  </nav>
</template>

<script>
  //import UserService from "../services/UserService"
  import TokenService from "../services/TokenService";
  import CurrentUser from "../services/CurrentUser";
  export default {
    name : 'navbar',
    data() {
      return {
        connected : false,
        user : {id : 0, name : ""}
      }
    },
    methods : {
      getUser() {
        if (!CurrentUser.user) {
          CurrentUser.updateUser().then(() => this.updateView())
        } else {
          this.updateView()
        }
      },
      disconnect() {
        TokenService.removeToken();
        CurrentUser.removeUser();
        this.connected = false;
        this.user = {id : 0, name : ""};
        this.$router.push("/");
      },
      updateView() {
        this.connected = true;
        this.user = CurrentUser.user;
      },
      discordUrl : () => "/api/discord/login"
    },
    mounted() {
      this.getUser()
    },
    watch: {
      "$route"(to, from) {
        this.getUser()
      }
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
      flex-direction: row;

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