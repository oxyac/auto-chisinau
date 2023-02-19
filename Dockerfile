FROM node as build-ui
WORKDIR /root/build-ui
RUN git clone https://github.com/oxyac/auto-chisinau-ui.git . \
    && npm install  \
    && npm run build

FROM gradle as build-server
WORKDIR /root/build-server
RUN git clone https://github.com/oxyac/auto-chisinau.git . \
    && gradle build

FROM bellsoft/liberica-openjre-alpine as deploy
ARG ALLOWED_CORS_ORIGINS
ARG UI_STATIC_FILES_DIR

EXPOSE 8081
RUN rm -rf /usr/share/nginx/html/*

COPY --from=build-ui /root/build-ui/dist /root/build-ui/dist
COPY --from=build-server /root/build-server/build/libs/app.jar /root/app.jar
COPY --from=build-server /root/build-server/start.sh /root/start.sh

WORKDIR /root
RUN chmod +x start.sh
ENTRYPOINT ["java", "-jar", "app.jar"]
