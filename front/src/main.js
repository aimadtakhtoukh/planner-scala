import Vue from 'vue'
import App from './App.vue'
import './assets/global.scss'
import 'bootstrap';
import 'bootstrap/dist/css/bootstrap.min.css';
import router from './routes'

Vue.config.productionTip = false

new Vue({
  router : router,
  render: h => h(App)
}).$mount('#app')
