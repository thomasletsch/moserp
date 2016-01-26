FROM java:8
ADD config-service-1.0.0-SNAPSHOT.jar app.jar
VOLUME /tmp
VOLUME /target
RUN bash -c 'touch /app.jar'
EXPOSE 8888
ENTRYPOINT ["java","-Xmx128M","-Djava.security.egd=file:/dev/./urandom","--spring.profiles.active=docker", "-jar","/app.jar"]
