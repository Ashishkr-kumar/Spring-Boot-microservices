FROM openjdk:17-jdk-slim
ARG AWS_ACCESS_KEY_ID
ARG AWS_SECRET_ACCESS_KEY

# Set the arguments as environment variables for the container
ENV AWS_ACCESS_KEY_ID=${AWS_ACCESS_KEY_ID}
ENV AWS_SECRET_ACCESS_KEY=${AWS_SECRET_ACCESS_KEY}
RUN mkdir -p /opt/app
ENV PROJECT_HOME /opt/app
COPY ./target/file-upload-s3-bucket-0.0.1-SNAPSHOT.jar $PROJECT_HOME/file-upload-s3-bucket.jar
WORKDIR $PROJECT_HOME
EXPOSE 8080
CMD ["java", "-jar", "./file-upload-s3-bucket.jar"]
