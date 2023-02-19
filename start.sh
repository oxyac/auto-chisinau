#!/bin/sh

systemctl start nginx
java -jar app.jar
