FROM node:20
WORKDIR .
COPY package*.json ./
RUN yarn install --force
COPY . .
EXPOSE 8080
CMD ["node", "app.js"]