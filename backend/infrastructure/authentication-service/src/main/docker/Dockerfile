FROM java:8
ADD authentication-service-1.0.0-SNAPSHOT.jar app.jar
VOLUME /tmp
VOLUME /target
RUN bash -c 'touch /app.jar'
EXPOSE 8899
ENTRYPOINT ["java","-Xmx128M","-Djava.security.egd=file:/dev/./urandom","-jar","/app.jar"]
