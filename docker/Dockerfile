FROM openjdk:8-jdk-alpine

LABEL maintainer="selimssevgi@gmail.com"

# Add a volume pointing to /tmp, spring writes to it
VOLUME /tmp

# Make port 8080 available to the world outside this container
EXPOSE 8080

# The application's jar file
ARG JAR_FILE=target/um-0.0.1-SNAPSHOT.jar

# Add the application's jar to the container
ADD ${JAR_FILE} um.jar

ENTRYPOINT ["java","-Djava.security.egd=file:/dev/./urandom","-jar","/um.jar"]

# docker image build -t todarch/um:latest -f docker/Dockerfile .
# docker container run -it --rm --name todarch-um todarch:um -p 8081:8080 todarch/um
# localhost:8081/non-secured/up
