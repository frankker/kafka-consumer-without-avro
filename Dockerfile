FROM adoptopenjdk:11-jdk-hotspot
LABEL maintainer="ict"

#ARG TERM=xterm
#ARG DEBIAN_FRONTEND=noninteractive

ENV MAIN_POSTGRES_USER=postgres \
    MAIN_POSTGRES_PASSWORD=example \
    MAIN_POSTGRES_HOST=postgres \
    MAIN_POSTGRES_PORT=5432 \
    MAIN_POSTGRES_TIMEOUT=30 \
#    PROFILE=Hilti,Batch\
    STRDEDUP=-XX:+UseStringDeduplication \
    GC_LOG_LEVEL=-Xlog:gc*=error \
#    ONTRACK3_MQ_HOST=activemq \
#    ONTRACK3_MQ_HOST_FAILOVER=activemq \
#    ONTRACK3_MQ_PORT=61616 \
#    ONTRACK3_MQ_USER=admin \
#    ONTRACK3_MQ_PASSWORD=admin \
#    ONTRACK3_MQ_SCHEME=tcp \
#    SEARCH_LABELS_SVC=localhost:8080 \
#    USER_MGMT_SVC=localhost:8081 \
#    CATALOGUE_DOC_MGMT_SVC=localhost:8082

RUN apt-get update && \
    apt-get upgrade -y && \
    apt-get install -y --no-install-recommends netcat && \
    rm -rf /var/lib/apt/lists/* && \
    useradd --create-home --comment "java user" java && \
    echo '#!/usr/bin/env bash\n\
    retries=0\n\
    echo -n "Waiting for ${MAIN_POSTGRES_HOST}:${MAIN_POSTGRES_PORT}"\n\
    while ! (nc -w 3 -z ${MAIN_POSTGRES_HOST} ${MAIN_POSTGRES_PORT}); do\n\
    sleep 1\n\
    echo -n "."\n\
    if [ $retries -eq ${MAIN_POSTGRES_TIMEOUT} ]; then\n\
    echo " Timeout, aborting."\n\
    exit 1\n\
    fi\n\
    retries=$(($retries+1))\n\
    done\n\
    exec java ${JAVA_OPTS} ${GC_LOG_LEVEL} ${STRDEDUP} -javaagent:/home/java/glowroot.jar \
                                                      -Dglowroot.agent.id=${GLOWROOT_AGENT_ID} \
                                                      -Dglowroot.agent.rollup.id=${GLOWROOT_ROLLUP_ID} \
                                                      -Dglowroot.collector.address=${GLOWROOT_COLLECTOR_ADDRESS} \
                                                      -jar /home/java/app.jar\n' > /entrypoint.sh && \
    chown java:java /entrypoint.sh && \
    chmod 0500 /entrypoint.sh

#COPY --chown=java:java "build/glowroot/glowroot.jar" "/home/java/glowroot.jar"
COPY --chown=java:java "build/libs/kafka-consumer*.jar" "/home/java/app.jar"

RUN chmod 0500 /home/java/*.jar

USER java:java
WORKDIR /home/java
EXPOSE 8080

ENTRYPOINT ["/entrypoint.sh"]
