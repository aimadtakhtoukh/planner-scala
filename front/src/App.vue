<template>
  <div id="app">
    <nav class="navigation-bar">
      <navbar></navbar>
    </nav>
    <section class="main-body">
      <router-view></router-view>
    </section>
  </div>
</template>

<script>
import Navbar from './components/Navbar.vue'

export default {
  name: 'app',
  components: {
    Navbar
  }
}
</script>

<style>
  #app {
    font-family: 'Avenir', Helvetica, Arial, sans-serif;
    -webkit-font-smoothing: antialiased;
    -moz-osx-font-smoothing: grayscale;
  }
</style>
