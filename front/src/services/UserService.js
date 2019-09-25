import axios from './AxiosDefaultConfig'
import Vue from 'vue';
import Vuex from 'vuex';

Vue.use(Vuex);

const UserService = {
  getSelf() {
    return axios.client().get("security/@me")
      .then(response => ({"status" : "ok", "data" : response.data}))
      .catch(error => ({"status" : "error", "data" : error.response}))
  },
  createUser(user) {
    return axios.client().post("user/add", user)
  }
};

export default UserService