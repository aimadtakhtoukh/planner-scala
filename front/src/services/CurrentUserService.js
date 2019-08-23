import axios from './axios_default'
import TokenService from './TokenService'

const CurrentUserService = {
    async getSelf() {
        const token = TokenService.getToken();
        if (token == null) return await null;
        let c = await axios.client().get("security/@me");
        return await c.data;
    }
};

export default CurrentUserService