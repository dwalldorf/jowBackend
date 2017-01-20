FROM kibana:5

RUN apt-get update && apt-get install -y netcat bzip2 curl

COPY entrypoint.sh /tmp/entrypoint.sh
RUN chmod +x /tmp/entrypoint.sh

COPY kibanaObjects.json /tmp/kibanaObjects.json
CMD ["/tmp/entrypoint.sh"]