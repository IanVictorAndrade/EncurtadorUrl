FROM ianvictor/encurtador_url:v1.0
ENV TZ="America/Porto_Velho"
COPY target/*.jar app.jar
ENTRYPOINT ["java","-jar","app.jar"]
