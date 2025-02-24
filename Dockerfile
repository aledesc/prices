FROM apache/beam_java17_sdk
LABEL authors="arquim"
ARG JAR_FILE=build/libs/price-0.0.1-SNAPSHOT.jar
COPY ${JAR_FILE} app.jar
ENTRYPOINT ["java","-jar","/app.jar"]


