#Where we start
FROM openjdk:14-alpine AS builder

#Get APK up to date
RUN apk update && apk upgrade

#Install Maven
RUN apk add maven

#Git
RUN apk add git
RUN mkdir /FuckYouNickBot
RUN git clone -b master https://github.com/Nicolazz92/FuckYouNickBot.git /FuckYouNickBot

#Build
RUN mvn -f /FuckYouNickBot clean install -DskipTests=true

#Build release image
FROM openjdk:14-alpine

#Copy result
WORKDIR /Executables
COPY --from=builder /FuckYouNickBot/target/ .

#Add user and group for running as unprivileged user
RUN addgroup -S appgroup && adduser -S appuser -G appgroup
USER appuser

#Define how to start
ENTRYPOINT ["java", "-jar", "FuckYouNickBot-0.0.1-SNAPSHOT.jar"]