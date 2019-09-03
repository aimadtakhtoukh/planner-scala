import axios from 'axios'
import TokenService from "./TokenService";
const AxiosDefaultConfig = {
    client() {
        axios.defaults.baseURL = process.env.VUE_APP_SERVER_URL;
        axios.defaults.headers.common = {"Authorization" : `Bearer ${TokenService.getToken()}`};
        return axios
    }
};

export default AxiosDefaultConfig;