FROM confluentinc/cp-kafka-connect:7.2.1
COPY ./plugins/ /usr/share/java
ENV CONNECT_PLUGIN_PATH="/usr/share/java,/usr/share/confluent-hub-components"
USER 1001
