FROM openjdk:17
ADD /target/TripsService-0.0.1-SNAPSHOT.jar tripservice.jar
ENTRYPOINT ["java", "-jar", "tripservice.jar"]
