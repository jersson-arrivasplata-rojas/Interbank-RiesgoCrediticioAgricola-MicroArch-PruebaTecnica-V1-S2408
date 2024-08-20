# Usa una imagen base de OpenJDK
FROM openjdk:17-jdk-slim

EXPOSE 7100

# Establece el directorio de trabajo dentro del contenedor
WORKDIR /app

# Copia el archivo JAR al contenedor
COPY target/agrrisk-api-auth-0.0.1-SNAPSHOT.jar /app/api-auth.jar

# Comando para ejecutar el JAR
ENTRYPOINT ["java", "-jar", "/app/api-auth.jar", "--spring.config.import=optional:configserver:http://server-config:7000"]

# docker build -t agrrisk-api-auth .