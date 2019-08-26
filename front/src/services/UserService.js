import axios from './AxiosDefaultConfig'
import Vue from 'vue';
import Vuex from 'vuex';

Vue.use(Vuex);

const UserService = {
  getSelf() {
    return axios.client().get("security/@me")
      .then(response => response.data)
      .catch(() => null)
  },
  createUser(user) {
    return axios.client().post("user/add", user)
  }
};

export default UserService