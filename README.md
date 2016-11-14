## owBackend

# Run
### In container
This project relies on docker. You can start everything by `make build; make run` or just redeploy the java package with `make redeploy`.

You can attach your debugger at `localhost:8000`. The application runs with  
`-agentlib:jdwp=transport=dt_socket,server=y,suspend=n,address=8000`

### Locally
You can also run the everything locally by starting the application with `mvn spring-boot:run -Dspring.profiles.active=dev`. 
