# Prognosis app (Solution Services)

This app connects with YouTrack and the other systems used in the Solution Services workflows and gives a visual representation of the team workload at the moment.

## Development usage

1. Install prerequisites: JDK 11, Node 12. Clone the repo.
2. Create `./src/main/resources/application.properties` file by using `./src/main/resources/application.defaults.properties` template.
3. Fill `youtrack.token` value with your permanent token.
4. Run `./gradlew bootRun` in terminal to start the application.
5. Open the browser at http://localhost:8080.

## Docker usage

1. Create `env.list` file near Dockerfile.
2. Fill `YOUTRACK_TOKEN` value with your permanent token.
3. Run `./build.sh` or `./build.cmd` to build and run a Docker container with the application
4. Open the browser at http://localhost:8080.

## Contacts

@daemos aka Eugene Dementjev
