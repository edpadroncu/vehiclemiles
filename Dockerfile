FROM openjdk:8-jdk-alpine
RUN mkdir -p /opt/vehiclemiles
RUN chmod -R 777 /opt/vehiclemiles
# Needed to fix 'Fontconfig warning: ignoring C.UTF-8: not a valid language tag'
ENV LANG en_GB.UTF-8
RUN apk add --update ttf-dejavu && rm -rf /var/cache/apk/*

ARG JAR_FILE
ADD ${JAR_FILE} /opt/vehiclemiles/app.jar
COPY src/main/resources/application.properties /opt/vehiclemiles/
ENTRYPOINT ["java","-Djava.security.egd=file:/dev/./urandom","-jar","/opt/vehiclemiles/app.jar", "--spring.config.location=file:/opt/vehiclemiles/application.properties"]
