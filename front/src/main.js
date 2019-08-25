import Vue from 'vue'
import App from './App.vue'
import './assets/global.scss'
import 'bootstrap';
import 'bootstrap/dist/css/bootstrap.min.css';
import router from './routes'
import { library } from '@fortawesome/fontawesome-svg-core'
import { faSpinner, faCheck, faBan, faEdit } from '@fortawesome/free-solid-svg-icons'
import { FontAwesomeIcon } from '@fortawesome/vue-fontawesome'

library.add(faSpinner);
library.add(faCheck);
library.add(faBan);
library.add(faEdit);
Vue.component('font-awesome-icon', FontAwesomeIcon);

Vue.config.productionTip = false

new Vue({
  router : router,
  render: h => h(App)
}).$mount('#app')
