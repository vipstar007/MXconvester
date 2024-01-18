# For Java 17, try this
FROM openjdk:17-oracle

# Set the timezone.
ENV TZ=Asia/Ho_Chi_Minh
RUN ln -snf /usr/share/zoneinfo/$TZ /etc/localtime && echo $TZ > /etc/timezone

# Refer to Maven build -> finalName
ARG JAR_FILE=/vas-backend-nrt-1.0.0.jar

ARG PROPERTIES_FILE=/application.properties

# cd /opt/app
WORKDIR /opt/app

# cp target/spring-boot-web.jar /opt/app/app.jar
COPY ${JAR_FILE} vas-backend-nrt-1.0.0.jar

COPY ${PROPERTIES_FILE} application.properties


# java -jar /opt/app/MockAPI-0.0.1.jar
ENTRYPOINT ["java","-jar","MockAPI-0.0.1.jar"]

