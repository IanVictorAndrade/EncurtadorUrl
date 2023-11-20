FROM openjdk:17
ENV TZ="America/Porto_Velho"
COPY target/*.jar app.jar
ENTRYPOINT ["java","-jar","app.jar"]
