FROM node:16.13.1-alpine
COPY ./src /frontal/src
COPY . /frontal/
WORKDIR /frontal
RUN npm install
RUN npm run build-prod

ENTRYPOINT [ "npm", "run", "start-prod" ]