# Usa una imagen base de OpenJDK
FROM openjdk:17-jdk-slim

EXPOSE 8761

# Establece el directorio de trabajo dentro del contenedor
WORKDIR /app

# Copia el archivo JAR al contenedor
COPY target/agrrisk-server-discovery-0.0.1-SNAPSHOT.jar /app/api-discovery.jar

# Comando para ejecutar el JAR
ENTRYPOINT ["java", "-jar", "/app/api-discovery.jar", "--spring.config.import=optional:configserver:http://server-config:7000", "--eureka.instance.hostname=server-discovery"]

# docker build -t agrrisk-server-discovery .