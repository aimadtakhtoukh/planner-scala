import axios from "./AxiosDefaultConfig"
import DateUtils from "./DateUtils";

const EntryService = {
  getUsersAndEntries() {
    return axios.client().get("/entry/withUser", {
      params : {
        from : DateUtils.today().format("YYYY-MM-DD"),
        to : DateUtils.nextWeekSunday().format("YYYY-MM-DD"),
      }
    })
      .then(response => response.data)
      .catch(() => [])
  },
  addEntry(entry) {
    return axios.client().post("/entry/add", entry)
  }
};

export default EntryService