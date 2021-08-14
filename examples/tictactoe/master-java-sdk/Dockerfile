FROM gradle:6.8-jdk11-hotspot as build
WORKDIR /workspace/app

COPY gradle gradle
COPY build.gradle settings.gradle gradlew ./
COPY src src

RUN gradle build -x test
RUN mkdir -p build/libs && (cd build/libs; jar -xf *.jar)

FROM adoptopenjdk:11-jre-hotspot
WORKDIR /app

ARG DEPENDENCY=/workspace/app/build/libs
COPY --from=build ${DEPENDENCY}/BOOT-INF /app/BOOT-INF
COPY --from=build ${DEPENDENCY}/META-INF /app/META-INF
COPY --from=build ${DEPENDENCY}/org /app/org

RUN groupadd -g 999 appuser && \
    useradd -r -u 999 -g appuser appuser
USER appuser

ENTRYPOINT ["java", "org.springframework.boot.loader.JarLauncher"]