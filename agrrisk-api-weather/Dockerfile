# Usa una imagen base de OpenJDK
FROM openjdk:17-jdk-slim

EXPOSE 7102

# Establece el directorio de trabajo dentro del contenedor
WORKDIR /app

# Copia el archivo JAR al contenedor
COPY target/agrrisk-api-weather-0.0.1-SNAPSHOT.jar /app/api-weather.jar

# Comando para ejecutar el JAR
ENTRYPOINT ["java", "-jar", "/app/api-weather.jar", "--spring.config.import=optional:configserver:http://server-config:7000", "--eureka.instance.hostname=server-discovery"]

# & minikube -p minikube docker-env | Invoke-Expression

# & minikube -p minikube docker-env -u | Invoke-Expression

# docker build -t agrrisk-api-weather .