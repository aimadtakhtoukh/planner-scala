FROM openjdk:8-jre
WORKDIR app
COPY target/universal/back-1.0.zip app/back.zip
RUN set -x && unzip -d app app/back.zip && rm app/back-1.0/bin/*.bat
EXPOSE 9000
WORKDIR app/back-1.0/bin
CMD ./back -Dhttp.port=9000 -Dplay.http.secret.key="thisdoesntmatterohmygodleavemealone"