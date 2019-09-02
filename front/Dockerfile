FROM node:8.12.0-alpine
WORKDIR /app
COPY src ./src
COPY server ./server
COPY public ./public
COPY vue.config.js vue-cli-serve.js package*.json ./
RUN npm install
RUN npm run build
WORKDIR /app/server
RUN npm install
EXPOSE 2443
ENTRYPOINT ["node","/app/server/server.js"]
