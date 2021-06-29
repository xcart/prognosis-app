# Prognosis app (Solution Services)

This app connects with YouTrack and the other systems used in the Solution Services workflows and gives a visual representation of the team workload at the moment.

## Development usage

1. Install prerequisites: JDK 11, Node 12. Clone the repo.
2. Create `./src/main/resources/application-default.properties` file. This is your local app settings
3. Fill required properties and set `auth.require` to `false`.
4. Run `./gradlew bootRun` in terminal to start the application.
5. Open the browser at http://localhost:8080.

## Docker usage

1. Create `./src/main/resources/application-default.properties` file or `./env.list` file.
2. Fill required properties.
3. Run `./build.sh` or `./build.cmd` to build and run a Docker container with the application
4. Open the browser at http://localhost:8080.

## Configuration properties

By default, the application is Google oAuth protected via default Spring Security stack. For development usage, it is recommended to set `auth.require` to `false`.

Properties can be set either via `user.properties` resource file or via environment variables. 

| Property name | ENV variable name | Description |
| --- | --- | --- |
| xb.password | XB_PASSWORD | (REQUIRED) XB API password  |
| youtrack.token | YOUTRACK_TOKEN | (REQUIRED) Youtrack API permanent token |
| auth.require | AUTH_REQUIRE | Is oAuth authentication required for Web and API (default: true) |
| auth.domains | AUTH_DOMAINS | Allowed oAuth user domains (default: x-cart.com,sellerlabs.com) |
| spring.security.oauth2.client.registration.google.client-id | ??? | Google oAuth Client Id (required when auth.require=true) |
| spring.security.oauth2.client.registration.google.client-secret | ??? | Google oAuth Client Secret (required when auth.require=true) |

## Contacts

@daemos aka Eugene Dementjev
