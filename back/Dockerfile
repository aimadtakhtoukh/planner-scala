FROM openjdk:8-jre
WORKDIR app
COPY target/universal/back-1.0.zip app/back.zip
EXPOSE 9000 9443
CMD /app/bin/start -Dhttps.port=9443 -Dplay.crypto.secret=secret