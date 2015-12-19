FROM java:8
VOLUME /tmp
ADD eureka-server-1.0.0-SNAPSHOT.jar /app.jar
ADD selfsigned.cer /selfsigned.cer
RUN bash -c 'keytool -import -file selfsigned.cer -alias selfsigned -noprompt -storepass changeit'
RUN bash -c 'touch /app.jar'
EXPOSE 8761
ENTRYPOINT ["java","-Xmx256M","-Djava.security.egd=file:/dev/./urandom","-jar","/app.jar"]
