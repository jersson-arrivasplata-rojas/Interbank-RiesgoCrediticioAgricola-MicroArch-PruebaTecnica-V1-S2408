# Usa una imagen base de OpenJDK
FROM openjdk:17-jdk-slim

EXPOSE 7000

# Establece el directorio de trabajo dentro del contenedor
WORKDIR /app

# Copia el archivo JAR al contenedor
COPY target/agrrisk-server-config-0.0.1-SNAPSHOT.jar /app/server-config.jar

# Comando para ejecutar el JAR
ENTRYPOINT ["java", "-jar", "/app/server-config.jar", "--eureka.instance.hostname=server-discovery"]

# & minikube docker-env | Invoke-Expression

# docker build -t agrrisk-server-config .

# kubectl apply -f server-config-deployment.yaml
