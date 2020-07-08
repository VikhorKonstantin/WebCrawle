FROM adoptopenjdk/openjdk11:alpine-jre

WORKDIR /app
# ensure we copy an application.jar into the container
COPY ./build/libs/webcrawler-0.0.1-SNAPSHOT.jar /app/webcrawler-0.0.1-SNAPSHOT.jar

EXPOSE 8080 27017

CMD [   "java",  "-jar", \
        "-DMONGODB_URI=mongodb://webcrawler-mongo:27017/webcrawler", \
        "/app/webcrawler-0.0.1-SNAPSHOT.jar" \
]
