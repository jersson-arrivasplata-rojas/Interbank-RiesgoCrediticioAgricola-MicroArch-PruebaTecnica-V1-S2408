# Usa una imagen base de OpenJDK
FROM openjdk:17-jdk-slim

EXPOSE 7002

# Establece el directorio de trabajo dentro del contenedor
WORKDIR /app

# Copia el archivo JAR al contenedor
COPY target/agrrisk-server-gateway-0.0.1-SNAPSHOT.jar /app/server-gateway.jar

# Comando para ejecutar el JAR
ENTRYPOINT ["java", "-jar", "/app/server-gateway.jar", "--spring.config.import=optional:configserver:http://server-config:7000", "--eureka.instance.hostname=server-discovery"]

# docker build -t agrrisk-server-gateway .

# spring.security.oauth2.client.registration.azure.redirect-uri=http://${eureka.instance.hostname}:7002/login/oauth2/code/azure
