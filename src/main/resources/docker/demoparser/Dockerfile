FROM golang:1.6

COPY wait-for-it.sh /usr/bin

RUN go get -u github.com/stegmannc/csgo-demoparser/cmd/demoparser-worker
RUN go install github.com/stegmannc/csgo-demoparser/cmd/demoparser-worker

ENTRYPOINT /usr/bin/wait-for-it.sh rabbitmq:5672 -- /go/bin/demoparser-worker