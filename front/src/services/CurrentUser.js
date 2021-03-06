import UserService from "./UserService";
import Vuex from "vuex";

const store = new Vuex.Store({
  state: {
    user : {},
    connected : false
  },
  mutations: {
    updateUser(state, user) {
      if (user) {
        state.user = user;
        state.connected = Object.keys(user).length !== 0
      }
    },
    removeUser(state) {
      state.user = {};
      state.connected = false
    }
  },
  actions: {
    updateUser({commit}) {
      return UserService.getSelf().then(response => {
        if (response.status === "error") {
          return response.data.data.error
        } else {
          const user = response.data;
          commit("updateUser", user);
          return user
        }
      })
    }
  }
});

export default store