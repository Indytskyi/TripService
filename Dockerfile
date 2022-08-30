FROM openjdk:17
ADD /target/TripsService-0.0.1-SNAPSHOT.jar tripservice.jar
ENTRYPOINT ["java", "-jar", "tripservice.jar"]
#ARG JAR_FILE=*.jar
#COPY ${JAR_FILE} tripservice.jar
#ENTRYPOINT ["java", "-jar", "tripservice.jar"]

#FROM openjdk:17
#WORKDIR /TripsService
#COPY . .
#RUN mvn clean install
#CMD mvn spring-boot:run