import Vue from 'vue'
import App from './App.vue'
import './assets/global.scss'
import 'bootstrap';
import 'bootstrap/dist/css/bootstrap.min.css';
import Route from './routes'

Vue.config.productionTip = false

new Vue({
  router : Route,
  render: h => h(App),
}).$mount('#app')
