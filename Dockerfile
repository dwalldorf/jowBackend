FROM java:8-jdk

RUN mkdir -p /usr/local/ow-backend
COPY target/owBackend-0.0.1-SNAPSHOT.jar /usr/local/ow-backend/owBackend.jar
CMD ["/usr/lib/jvm/java-8-openjdk-amd64/bin/java", "-agentlib:jdwp=transport=dt_socket,server=y,suspend=n,address=8000", "-jar", "/usr/local/ow-backend/owBackend.jar"]

EXPOSE 8080
EXPOSE 8000
EXPOSE 5005