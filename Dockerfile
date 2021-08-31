FROM public.ecr.aws/c9h1k2b9/maven:latest as build

WORKDIR /workspace/app
COPY pom.xml .
COPY src src
RUN mvn install -DskipTests

FROM public.ecr.aws/compose-x/amazoncorretto:11
ARG JAR_FILE=/workspace/app/target/*.jar
COPY --from=build ${JAR_FILE} app.jar
ENTRYPOINT ["java","-jar","/app.jar"]
EXPOSE 80