# Etapa de compilación, un docker especifico, que se etiqueta como build
FROM gradle:jdk21-alpine AS build

# Directorio de trabajo
WORKDIR /app

# Copia los archivos build.gradle y src de nuestro proyecto
COPY build.gradle.kts .
COPY gradlew .
COPY gradle gradle
COPY src src

# Configura la variable de entorno DOCKER_HOST, esto es para que el contenedor pueda comunicarse con el host
# En Windows se usa host.docker.internal, en Linux y macOS se puede usar localhost
ARG DOCKER_HOST_ARG=tcp://host.docker.internal:2375
ENV DOCKER_HOST=$DOCKER_HOST_ARG


# Compila y construye el proyecto, podemos evitar los test evitando con -x test
RUN ./gradlew build

# Etapa de ejecución, un docker especifico, que se etiqueta como run
# Con una imagen de java, solo neceistamos el jre
FROM eclipse-temurin:21-jre-alpine AS run

# Directorio de trabajo
WORKDIR /app

# Copia el jar de la aplicación, ojo que esta en la etapa de compilación, etiquetado como build
# Cuidado con la ruta definida cuando has copiado las cosas en la etapa de compilación
# Para copiar un archivo de una etapa a otra, se usa la instrucción COPY --from=etapaOrigen
COPY --from=build /app/build/libs/*SNAPSHOT.jar /app/my-app.jar

# Ejecuta el jar
ENTRYPOINT ["java","-jar","/app/my-app.jar"]