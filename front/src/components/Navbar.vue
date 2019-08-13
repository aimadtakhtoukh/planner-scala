<template>
  <nav class="topnav">
    <a href="/">
      <img
        class="logo"
        src="/foresight-main.svg"
        alt="Calendar"/>
    </a>

    <div class="right-floating" v-if="connected">
      <span>{{user}}</span>
      <button class="btn disconnect-button">Déconnexion</button>
    </div>
    <div class="right-floating" v-else>
      <a class="btn connect-button" :href="discordUrl()">Se connecter</a>
    </div>
  </nav>
</template>

<script>
import CurrentUserService from "../services/CurrentUserService"
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
            CurrentUserService.getSelf("9hySlMzJvCzJKsns4IYXBmM6SXma3Z").then(user => this.user = user)
        },
        discordUrl : () => "https://discordapp.com/api/oauth2/authorize?client_id=609309569721565184&redirect_uri=http%3A%2F%2Flocalhost%3A8080%2Ftoken%2Fdiscord&response_type=code&scope=identify"
    },
    mounted() {
        this.getUser()
    }
}
</script>

<style lang="scss">
.topnav {
  background-color: #333;
  overflow: hidden;
  display: flex;
  width: 100%;

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