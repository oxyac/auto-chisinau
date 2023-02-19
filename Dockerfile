FROM gradle:jdk17-focal as build

WORKDIR /build

RUN mkdir -p /root/.ssh \
    && chmod 0700 /root/.ssh \
    && ssh-keyscan -t rsa github.com >> /root/.ssh/known_hosts \
    && git clone https://github.com/oxyac/auto-chisinau.git . \
    && gradle build

#EXPOSE 7880
FROM bellsoft/liberica-openjre-debian:17
COPY --from=build /build/build/libs/app.jar app.jar

WORKDIR /build-ui

FROM node:19-buster as build-ui

RUN  git clone https://github.com/oxyac/auto-chisinau-ui.git . \
    && npm install && npm run build \

FROM nginx:stable-alpine as production-stage
EXPOSE 8081
COPY nginx.conf /etc/nginx/conf.d/default.conf
RUN rm -rf /usr/share/nginx/html/*
COPY --from=build-ui /build/dist /usr/share/nginx/html
CMD ["nginx", "-g", "daemon off;"]

ENTRYPOINT ["java", "-jar", "app.jar"]
