FROM java:8
ADD product-1.0.0-SNAPSHOT.jar app.jar
ADD selfsigned.cer /selfsigned.cer
RUN bash -c 'keytool -import -file selfsigned.cer -alias selfsigned -noprompt -storepass changeit'
VOLUME /tmp
VOLUME /target
RUN bash -c 'touch /app.jar'
EXPOSE 9303
ENTRYPOINT ["java","-Xmx256M","-Djava.security.egd=file:/dev/./urandom","-jar","/app.jar"]
