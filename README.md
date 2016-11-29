# owBackend

An online app to track your CS:GO overwatch cases and analyze your competitive demos.

# The Application
## Relies on
The deployed software relies on the following docker setup (also see [docker-compose.yml](https://github.com/dwalldorf/jowBackend/blob/master/docker-compose.yml)):
* redis instance for session handling
* rabbitmq for passing demo files to:
* container running [stegmannc's demo parser](https://github.com/stegmannc/csgo-demoparser), polling rabbitmq
* mongo instance for general data storage

## Run it
### In container
This project relies on docker. You can start everything by `make build; make run` or just redeploy the java package with `make redeploy`.

You can attach your debugger at `localhost:8000`. The application runs with  
`-agentlib:jdwp=transport=dt_socket,server=y,suspend=n,address=8000`

### Locally
You can also run the everything locally by starting the application with `mvn spring-boot:run -Dspring.profiles.active=dev`. This will require a locally running mongodb, redis and rabbitmq.

## Tests
Run test with `make test` or `make integration-test`.
