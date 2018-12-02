FROM openjdk:11-jdk-slim

WORKDIR /app

ENV JAVA_OPTS=""

# Adding executable jar
ADD target/roo-fat.jar /app/roo-fat.jar

# Adding configuration files
ADD target/classes/*.yml  /app/config/

EXPOSE 8080

ENTRYPOINT exec java $JAVA_OPTS \
    -Djava.security.egd=file:/dev/./urandom \
    -jar /app/roo-fat.jar \
    --spring.config.location=classpath:/config
