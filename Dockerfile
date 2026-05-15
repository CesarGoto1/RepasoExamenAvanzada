# Etapa 1: Build
FROM gradle:8-jdk17 AS build
WORKDIR /app

# Copiar archivos de configuración de Gradle para aprovechar la caché de capas
COPY build.gradle settings.gradle ./
COPY gradle ./gradle
COPY gradlew ./
RUN chmod +x gradlew

# Descargar dependencias (esto ayuda a que los builds sean más rápidos si no cambian las dependencias)
RUN ./gradlew dependencies --no-daemon

# Copiar el código fuente y compilar
COPY src ./src
RUN ./gradlew bootJar --no-daemon -x test

# Etapa 2: Run
FROM eclipse-temurin:17-jre-jammy
WORKDIR /app

# Copiar el JAR compilado desde la etapa de build
# El nombre del JAR suele ser SpringLab-0.0.1-SNAPSHOT.jar basado en build.gradle
COPY --from=build /app/build/libs/*.jar app.jar

# Exponer el puerto que usa la aplicación (según application.yml)
EXPOSE 8085

# Ejecutar la aplicación
# Usamos sh -c para que la variable de entorno PORT de Render sea expandida correctamente
# Activamos el perfil 'render' para usar H2
ENTRYPOINT ["sh", "-c", "java -Dspring.profiles.active=render -Dserver.port=${PORT:-8085} -jar app.jar"]
