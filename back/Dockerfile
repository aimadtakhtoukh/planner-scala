FROM openjdk:8-jre
WORKDIR app
COPY target/universal/back-1.0.zip app/back.zip
RUN set -x && unzip -d app app/back.zip && rm app/back-1.0/bin/*.bat && mv app/back-1.0/bin* app/back-1.0/bin/start
EXPOSE 9000
CMD /app/back-1.0/bin/start -Dhttp.port=9000