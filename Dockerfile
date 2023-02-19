FROM archlinux:latest as base
RUN pacman -Sy --noconfirm reflector fish nano
RUN reflector --latest 5 --sort rate --save /etc/pacman.d/mirrorlist

## base builder
FROM base as builder-base
RUN pacman -S --noconfirm git openssh gradle nodejs-lts-gallium npm nginx
RUN ssh-keyscan -t rsa github.com >> /root/.ssh/known_hosts

FROM builder-base as build-ui
WORKDIR /root/build-ui
RUN git clone https://github.com/oxyac/auto-chisinau-ui.git . \
    && npm install  \
    && npm run build

FROM builder-base as build-server
WORKDIR /root/build-server
RUN git clone https://github.com/oxyac/auto-chisinau.git . \
    && gradle build

FROM builder-base as deploy
EXPOSE 8081
RUN rm -rf /usr/share/nginx/html/*

COPY --from=build-ui /root/build-ui/dist /usr/share/nginx/html/
COPY --from=build-server /root/build-server/nginx.conf /etc/nginx/conf.d/default.conf
COPY --from=build-server /root/build-server/root-nginx.conf /etc/nginx/nginx.conf
COPY --from=build-server /root/build-server/build/libs/app.jar /root/app.jar
COPY --from=build-server /root/build-server/start.sh /root/start.sh

WORKDIR /root
RUN chmod +x start.sh
ENTRYPOINT ["/bin/sh", "start.sh"]
