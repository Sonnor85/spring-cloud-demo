FROM adoptopenjdk/openjdk11:alpine
VOLUME /tmp

# tzdata is required for setting container timezone
RUN apk add --update tzdata
# set timezone in image
RUN cp /usr/share/zoneinfo/Europe/Budapest /etc/localtime

COPY target/authentication-0.0.1-SNAPSHOT.jar /app/authentication.jar
EXPOSE 8080
ENTRYPOINT ["java","-jar","app/authentication.jar"]
