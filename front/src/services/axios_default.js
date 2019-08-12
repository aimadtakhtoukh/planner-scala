import axios from 'axios'
export default class {
    static client(token) {
        axios.defaults.baseURL = "http://localhost:9000/";
        axios.defaults.headers.common = {"Authorization" : `Bearer ${token}`};
        return axios
    }
}