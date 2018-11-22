FROM java:openjdk-8-jre-alpine

ENV APP_SLEEP 5

# add directly the jar
ADD *.jar /profit-sharing-rules.jar

RUN sh -c 'touch /profit-sharing-rules.jar'
VOLUME /tmp
EXPOSE 8082 5701/udp
CMD echo "The application will start in ${APP_SLEEP} s..." && \
    sleep ${APP_SLEEP} && \
    java -Djava.security.egd=file:/dev/./urandom -jar /profit-sharing-rules.jar
