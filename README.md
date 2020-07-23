# Prognosis app (Solution Services)

This app connects with YouTrack and the other systems used in the Solution Services workflows and gives a visual representation of the team workload at the moment.

## Development usage

1. Install prerequisites: JDK 11, Node 12. Clone the repo.
2. Create `./src/main/resources/user.properties` file.
3. Fill `youtrack.token` value with your permanent token.
4. Run `./gradlew bootRun` in terminal to start the application.
5. Open the browser at http://localhost:8080.

## Docker usage

1. Create `./src/main/resources/user.properties` file or `./env.list` file.
2. Fill `YOUTRACK_TOKEN` value with your permanent token.
3. Run `./build.sh` or `./build.cmd` to build and run a Docker container with the application
4. Open the browser at http://localhost:8080.

## Configuration properties

Properties can be set either via `user.properties` resource file or via environment variables. 

| Property name | ENV variable name | Description |
| --- | --- | --- |
| youtrack.token | YOUTRACK_TOKEN | Youtrack API permanent token, required |
| auth.user | AUTH_USER | Application BasicAuth username (default: solutions) |
| auth.password | AUTH_PASSWORD | Application BasicAuth password |

## Contacts

@daemos aka Eugene Dementjev
