const bodyParser = require('body-parser');
const api = require('./server/discord-oauth2');

module.exports = app => {
    app.use(bodyParser.json())
    app.use('/api/discord', api)
}