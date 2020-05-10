FROM adoptopenjdk/openjdk11:alpine
VOLUME /tmp

# tzdata is required for setting container timezone
RUN apk add --update tzdata
# set timezone in image
RUN cp /usr/share/zoneinfo/Europe/Budapest /etc/localtime

COPY target/gateway-0.0.1-SNAPSHOT.jar /app/gateway.jar
EXPOSE 8080
ENTRYPOINT ["java","-jar","app/gateway.jar"]
