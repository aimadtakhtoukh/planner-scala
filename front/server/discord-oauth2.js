const express = require('express');
const fetch = require('node-fetch');
const btoa = require('btoa');
const { catchAsync } = require('./utils');
const https = require('https');

const router = express.Router();

const CLIENT_ID = process.env.CLIENT_ID;
const CLIENT_SECRET = process.env.CLIENT_SECRET;
const redirect = process.env.REDIRECT_URL + '/api/discord/callback';
const redirectEncoded = encodeURIComponent(redirect);
const scope = "identify";

router.get('/login', (req, res) => {
    res.redirect(`https://discordapp.com/oauth2/authorize?client_id=${CLIENT_ID}&scope=${scope}&response_type=code&redirect_uri=${redirectEncoded}`);
});

router.get('/callback', catchAsync(async (req, res) => {
  if (!req.query.code) throw new Error('NoCodeProvided');
  const code = req.query.code;
  let params = new URLSearchParams()
  params.append('client_id', CLIENT_ID);
  params.append('client_secret', CLIENT_SECRET);
  params.append('grant_type', 'authorization_code');
  params.append('code', code);
  params.append('redirect_uri', redirect);
  params.append('scope', scope);
  const response = await fetch(`https://discordapp.com/api/oauth2/token`,
    {
        method: 'POST',
        body : params
    }
  );
  const json = await response.json();
  res.redirect(`/token/discord/?access_token=${json.access_token}&refresh_token=${json.refresh_token}&expires_in=${json.expires_in}`);
}));

router.get('/valid', catchAsync(async (req, res) => {
  const authorization = req.headers.authorization;
  const options = {
    host: 'discordapp.com',
    port: 443,
    path: '/api/users/@me',
    method: 'GET',
    headers: {
      "Authorization" : authorization
    }
  };
  https.request(options, (r) => {
    if (r.statusCode !== 200) {
      res.status(401).send({})
    } else {
      res.status(200).send({})
    }
  }).end();
}));

router.get('/refresh', catchAsync(async (req, res) => {
  if (!req.query.refresh_token) throw new Error('NoCodeProvided');
  const refresh_token = req.query.refresh_token;
  let params = new URLSearchParams()
  params.append('client_id', CLIENT_ID);
  params.append('client_secret', CLIENT_SECRET);
  params.append('grant_type', 'refresh_token');
  params.append('refresh_token', refresh_token);
  params.append('redirect_uri', redirect);
  const response = await fetch(`https://discordapp.com/api/oauth2/token`,
    {
      method: 'POST',
      body : params
    }
  );
  const json = await response.json();
  res.status(200).send({
    access_token : json.access_token,
    refresh_token : json.refresh_token,
    expires_in : json.expires_in
  })
}));

module.exports = router;