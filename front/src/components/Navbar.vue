<template>
  <nav class="topnav">
    <a href="/">
      <img
        class="logo"
        src="/foresight-main.svg"
        alt="Calendar"/>
    </a>

    <div class="right-floating">
      <span>{{user}}</span>
      <button class="btn disconnect-button">Déconnexion</button>
    </div>
  </nav>
</template>

<script>
import CurrentUserService from "../services/CurrentUserService"
export default {
    name : 'navbar',
    data() {
        return {
            user : {id : 0, name : ""}
        }
    },
    methods : {
        getUser() {
            CurrentUserService.getSelf("9hySlMzJvCzJKsns4IYXBmM6SXma3Z").then(user => console.log(user))//this.user = user)
        }
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
  }
}
</style>