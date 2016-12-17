FROM java:8-jdk

EXPOSE 8080

ENV OW_HOME /usr/local/ow-backend

RUN mkdir -p $OW_HOME
COPY target/owBackend.jar $OW_HOME/owBackend.jar
CMD /usr/lib/jvm/java-8-openjdk-amd64/bin/java -jar $OW_HOME/owBackend.jar