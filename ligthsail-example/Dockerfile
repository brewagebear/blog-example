# syntax=docker/dockerfile:experimental
FROM openjdk:11-jdk AS build-stage
WORKDIR /workspace/app

COPY src /workspace/app/src
COPY gradle /workspace/app/gradle
COPY gradlew settings.gradle build.gradle /workspace/app/

RUN --mount=type=cache,target=/root/.gradle ./gradlew clean build -x test
RUN mkdir -p build/dependencies && (cd build/dependencies; jar -xf ../libs/ligthsail-example-1.0-SNAPSHOT.jar)

FROM openjdk:11-jre-slim
WORKDIR /tmp
ARG DEPENDENCY=/workspace/app/build/dependencies

COPY --from=build-stage ${DEPENDENCY}/BOOT-INF/lib ./app/lib
COPY --from=build-stage ${DEPENDENCY}/META-INF ./app/META-INF
COPY --from=build-stage ${DEPENDENCY}/BOOT-INF/classes ./app/

ENTRYPOINT ["java","-cp","app:app/lib/*","io.github.brewagebear.LigthsailApp"]