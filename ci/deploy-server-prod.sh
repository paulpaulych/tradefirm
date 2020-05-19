#!/bin/sh

for i in server/build/libs/server*.war
do
  mv "$i" "server/build/libs/server.war"
done

curl -T "server/build/libs/server.war" \
      -u "$PROD_TOMCAT_USERNAME:$PROD_TOMCAT_PASSWORD" \
 "http://$PROD_TOMCAT_HOST:$PROD_TOMCAT_PORT/manager/text/deploy?path=/tradefirm"
