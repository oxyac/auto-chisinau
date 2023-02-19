#!/bin/sh

echo 127.0.0.1 auto.loc  >> /etc/hosts

nginx
java -jar app.jar
