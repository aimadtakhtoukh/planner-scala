import axios from './axios_default'

export default class CurrentUserService {

    static async getSelf(token) {
        let c = await axios.client(token).get("security/@me");
        return await c.data;
    }

}