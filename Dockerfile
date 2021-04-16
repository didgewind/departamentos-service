FROM openjdk:8-jdk-alpine
COPY target/departamentos-service-0.1.jar departamentos-service-0.1.jar
EXPOSE 7777
ENTRYPOINT ["java", "-DEUREKA_SERVER=http://eureka-service:1111/eureka","-jar","/departamentos-service-0.1.jar"]