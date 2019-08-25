import axios from './AxiosDefaultConfig'

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