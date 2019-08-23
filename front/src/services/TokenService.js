const accessTokenKey = "access_token";
const TokenService = {
    saveToken(code) {
        localStorage.setItem(accessTokenKey, code);
    },
    getToken() {
        return localStorage.getItem(accessTokenKey);
    }
};
export default TokenService