FROM java:8
MAINTAINER kobelee
COPY ./target/Client-1.0-SNAPSHOT.jar /usr/local/Client-1.0-SNAPSHOT.jar
ENTRYPOINT ["java","-jar","/usr/local/Client-1.0-SNAPSHOT.jar"]
