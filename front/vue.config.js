const expressAPI = require("./vue-cli-serve");

module.exports = {
    runtimeCompiler: true,
    devServer : {
        before : expressAPI
    }
};