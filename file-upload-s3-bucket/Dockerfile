FROM openjdk:17.0.9-jdk-slim
RUN mkdir -p /opt/app
ENV PROJECT_HOME /opt/app
COPY ./target/file-upload-s3-bucket-0.0.1-SNAPSHOT.jar $PROJECT_HOME/file-upload-s3-bucket.jar
WORKDIR $PROJECT_HOME
EXPOSE 8080
CMD ["java", "-jar", "./file-upload-s3-bucket.jar"]
