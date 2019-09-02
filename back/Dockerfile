FROM openjdk:8-jre
WORKDIR app
COPY target/universal/back-1.0.zip app/back.zip
RUN set -x && unzip -d app app/back.zip && rm app/bin/*.bat && mv app/bin/* app/bin/start
EXPOSE 9000
CMD /app/bin/start -Dhttp.port=9000