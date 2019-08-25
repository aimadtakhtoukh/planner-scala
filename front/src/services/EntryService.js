import axios from "./AxiosDefaultConfig"

const EntryService = {
  getUsersAndEntries() {
    return axios.client().get("/entry/withUser")
      .then(response => response.data)
      .catch(() => [])
  },
  addEntry(entry) {
    return axios.client().post("/entry/add", entry)
  }
};

export default EntryService