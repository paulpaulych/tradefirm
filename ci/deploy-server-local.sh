#!/bin/sh

for i in server/build/libs/server*.war
do
  mv "$i" "server/build/libs/server.war"
done

curl -T "server/build/libs/server.war" \
      -u "PROD_TOMCAT_USERNAME:s3cret" \
 "http://localhost:8081/manager/text/deploy?path=/tradefirm"
